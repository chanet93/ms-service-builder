package com.glic.hsm.nshield;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.CreateKeystoreReq;
import com.glic.hsm.nshield.response.CreateKeystoreResp;
import com.glic.hsm.payshield.exception.HsmException;

/**
 * Created by erwine1 on 19/12/16.
 */
public class CreateKeystoreCommand extends AbstractCommand implements Command<CreateKeystoreReq,CreateKeystoreResp> {

   private static final Logger LOGGER = Logger.getLogger(CreateKeystoreCommand.class.getName());

   @Override
   public CreateKeystoreResp execute(CreateKeystoreReq req) throws HsmException {

      CreateKeystoreResp result = new CreateKeystoreResp();

      try {

         KeyStore ks =  CryptoUtils.getKeystoreByProvider(getProvider());
         ks.load(null, req.getPassphrase());

         try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ks.store(outputStream, req.getPassphrase());
            result.setKeystoreAlias(new String(outputStream.toByteArray()));
            result.setErrorCode(NShieldErrorCode.NO_ERROR);
         }
      } catch (IOException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.ERROR_PERSISTING_LOADING_KEYSTORE);
      } catch (NoSuchAlgorithmException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.NO_SUCH_ALGORITHM);
      } catch (CertificateException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.CERTIFICATE_ERROR);
      } catch (NoSuchProviderException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.NO_SUCH_PROVIDER);
      } catch (KeyStoreException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.KEYSTORE_ERROR);
      }
      return result;
   }
}
