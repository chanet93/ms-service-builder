package com.glic.hsm.nshield;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.LoadKeystoreReq;
import com.glic.hsm.nshield.response.LoadKeystoreResp;
import com.glic.hsm.payshield.exception.HsmException;

/**
 * Created by erwine1 on 19/12/16.
 */
public class LoadKeystoreCommand extends AbstractCommand implements Command<LoadKeystoreReq,LoadKeystoreResp> {

   @Override
   public LoadKeystoreResp execute(LoadKeystoreReq req) throws HsmException {
      LoadKeystoreResp result = new LoadKeystoreResp();

      if(StringUtils.isEmpty(req.getKeystoreAlias())){
         result.setErrorCode(NShieldErrorCode.EMPTY_OR_INVALID_DATA);
         return result;
      }
      try {

         KeyStore keystore =  CryptoUtils.getKeystoreByProvider(getProvider());
         keystore.load(
               IOUtils.toInputStream(req.getKeystoreAlias(), StandardCharsets.UTF_8), req.getPassphrase());
         result.setKeyStore(keystore);
         result.setErrorCode(NShieldErrorCode.NO_ERROR);
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
