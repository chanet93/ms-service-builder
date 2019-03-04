package com.glic.hsm.nshield;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.GenerateSymmetricKeyReq;
import com.glic.hsm.nshield.response.GenerateSymmetricKeyResp;

/**
 * @author erwine1
 */
public class GenerateSymmetricKeyCommand extends AbstractCommand implements Command<GenerateSymmetricKeyReq, GenerateSymmetricKeyResp> {

   private static final Logger LOGGER = Logger.getLogger(GenerateSymmetricKeyCommand.class.getName());

   @Override
   public GenerateSymmetricKeyResp execute(GenerateSymmetricKeyReq req) {

      LOGGER.log(Level.FINE , "Generating symmetric key for "+req.getAlgorithmType().alias() +" size "+req.getAlgorithmType().size());

      GenerateSymmetricKeyResp result = new GenerateSymmetricKeyResp();

      try {

         KeyGenerator keyGenerator =  CryptoUtils.getKeyGeneratorByProvider(provider,req.getAlgorithmType());

         keyGenerator.init(req.getAlgorithmType().size());
         result.setSecretKey(keyGenerator.generateKey());
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
