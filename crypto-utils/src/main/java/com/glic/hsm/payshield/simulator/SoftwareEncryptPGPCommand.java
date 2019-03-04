package com.glic.hsm.payshield.simulator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.utils.KeyUtil;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.request.EncryptPGPReq;
import com.glic.hsm.payshield.response.EncryptPGPResp;

/**
 * Created by erwine1 on 08/09/16.
 */
public class SoftwareEncryptPGPCommand implements Command<EncryptPGPReq, EncryptPGPResp> {

   private static final Logger LOGGER = Logger.getLogger(SoftwareEncryptPGPCommand.class.getName());

   @Override
   public EncryptPGPResp execute(EncryptPGPReq req) throws HsmException {
      CryptoUtils cryptoUtils = new CryptoUtils();

      EncryptPGPResp response = new EncryptPGPResp();
      response.setErrorCode(ErrorsDef.NO_ERROR);
      response.setHeader(req.getHeader());

      try (InputStream inputStream = new ByteArrayInputStream(req.getPublicKey())) {
         try {
            PGPPublicKey publicKey = KeyUtil.findPublicKey(inputStream);
            byte[] result = cryptoUtils.encryptPGP(req.getData(), publicKey);
            response.setEncrypted(result);
         } catch (IOException e) {
            response.setErrorCode(ErrorsDef.PGP_ENCRYPT_ERROR);
            LOGGER.log(Level.SEVERE, "Error during PGP encryption ", e);
         } catch (PGPException e) {
            response.setErrorCode(ErrorsDef.PGP_ENCRYPT_ERROR);
            LOGGER.log(Level.SEVERE, "Error during PGP encryption", e);
         }
      } catch (IOException e) {
         response.setErrorCode(ErrorsDef.PGP_ENCRYPT_ERROR);
         LOGGER.log(Level.SEVERE, "Error during PGP encryption", e);
      }

      return response;
   }
}
