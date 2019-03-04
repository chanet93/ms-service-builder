package com.glic.hsm.payshield.simulator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.openpgp.PGPException;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.DecryptPGPReq;
import com.glic.hsm.payshield.response.DecryptPGPResp;

/**
 * Created by erwine1 on 08/09/16.
 */
public class SoftwareDecryptPGPCommand implements Command<DecryptPGPReq, DecryptPGPResp> {

   private static final Logger LOGGER = Logger.getLogger(SoftwareDecryptPGPCommand.class.getName());

   @Override
   public DecryptPGPResp execute(DecryptPGPReq req) {

      byte[] encrypted = req.getEncrypted();

      DecryptPGPResp resp = new DecryptPGPResp();

      try(InputStream is = new ByteArrayInputStream(req.getSecretKeys());){
         try {
            CryptoUtils crypto = new CryptoUtils();
            byte[] decryptedData = crypto.decryptPGP(encrypted, is, req.getPassphrase());
            resp.setDecrypted(decryptedData);
            resp.setErrorCode(ErrorsDef.NO_ERROR);
         } catch (PGPException e) {
            resp.setErrorCode(ErrorsDef.INTERNAL_ERROR);
            LOGGER.log(Level.SEVERE, "Exception thrown while encrypting (PGP)", e);
         }
      } catch (IOException e) {
         resp.setErrorCode(ErrorsDef.INTERNAL_ERROR);
         LOGGER.log(Level.SEVERE, "Exception thrown while encrypting (PGP)", e);
      }

      return resp;
   }
}
