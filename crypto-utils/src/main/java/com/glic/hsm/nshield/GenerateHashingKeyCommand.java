package com.glic.hsm.nshield;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.GenerateHashingKeyReq;
import com.glic.hsm.nshield.response.GenerateHashKeyResp;

/**
 * @author erwine1
 */
public class GenerateHashingKeyCommand extends AbstractCommand implements Command<GenerateHashingKeyReq, GenerateHashKeyResp> {

   private static final Logger LOGGER = Logger.getLogger(GenerateHashingKeyCommand.class.getName());

   @Override
   public GenerateHashKeyResp execute(GenerateHashingKeyReq req) {

      LOGGER.log(Level.FINE , "Generating hashing key for "+req.getAlgorithmType().alias() +" size "+req.getAlgorithmType().size());

      GenerateHashKeyResp result = new GenerateHashKeyResp();

      try {
         KeyGenerator keyGenerator =  CryptoUtils.getKeyGeneratorByProvider(provider,req.getAlgorithmType());
         keyGenerator.init(req.getAlgorithmType().size());
         SecretKey key = keyGenerator.generateKey();
         result.setSecretKey(key);
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
