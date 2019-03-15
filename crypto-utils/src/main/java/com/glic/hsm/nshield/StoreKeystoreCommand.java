package com.glic.hsm.nshield;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.StoreKeystoreReq;
import com.glic.hsm.nshield.response.StoreKeystoreResp;
import com.glic.hsm.payshield.exception.HsmException;


public class StoreKeystoreCommand extends AbstractCommand implements Command<StoreKeystoreReq, StoreKeystoreResp> {

   @Override
   public StoreKeystoreResp execute(StoreKeystoreReq req) throws HsmException {

      StoreKeystoreResp result = new StoreKeystoreResp();

      try {

         if (StringUtils.isEmpty(req.getKeystoreAlias()) || StringUtils.isEmpty(req.getKeyAlias()) || Objects.isNull(req.getKey())) {
            result.setErrorCode(NShieldErrorCode.EMPTY_OR_INVALID_DATA);
            return result;
         }

         KeyStore keystore = CryptoUtils.getKeystoreByProvider(getProvider());
         keystore.load(IOUtils.toInputStream(req.getKeystoreAlias(), StandardCharsets.UTF_8), req.getPassphrase());

         keystore.setKeyEntry(req.getKeyAlias(), req.getKey(), req.getPassphrase(), null);

         try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            keystore.store(outputStream, req.getPassphrase());
         }

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