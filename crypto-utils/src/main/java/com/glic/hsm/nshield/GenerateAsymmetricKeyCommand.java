package com.glic.hsm.nshield;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.GenerateAsymmetricKeyReq;
import com.glic.hsm.nshield.response.GenerateAsymmetricKeyResp;
import com.glic.hsm.payshield.exception.HsmException;

/**
 * @author erwine1
 */
public class GenerateAsymmetricKeyCommand extends AbstractCommand implements Command<GenerateAsymmetricKeyReq, GenerateAsymmetricKeyResp> {

   @Override
   public GenerateAsymmetricKeyResp execute(GenerateAsymmetricKeyReq req) throws HsmException {

      GenerateAsymmetricKeyResp result = new GenerateAsymmetricKeyResp();

      try {
         KeyPairGenerator keyPairGenerator = CryptoUtils.getKeyPairGeneratorByProvider(getProvider(),req.getAlgorithmType());
         result.setKeyPair(keyPairGenerator.generateKeyPair());
         result.setErrorCode(NShieldErrorCode.NO_ERROR);
      } catch (NoSuchAlgorithmException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.NO_SUCH_ALGORITHM);
      } catch (NoSuchProviderException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.NO_SUCH_PROVIDER);
      }

      return result;
   }

}
