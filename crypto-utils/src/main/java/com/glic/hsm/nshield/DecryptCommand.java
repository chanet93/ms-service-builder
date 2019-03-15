package com.glic.hsm.nshield;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.ArrayUtils;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.DecryptReq;
import com.glic.hsm.nshield.response.DecryptResp;


public class DecryptCommand extends AbstractCommand implements Command<DecryptReq, DecryptResp> {

   private static final Logger LOGGER = Logger.getLogger(DecryptCommand.class.getName());

   @Override
   public DecryptResp execute(DecryptReq req) {

      DecryptResp result = new DecryptResp();

      if (ArrayUtils.isEmpty(req.getData())) {
         result.setErrorCode(NShieldErrorCode.EMPTY_OR_INVALID_DATA);
         return result;
      }

      try {

         Cipher encCipher  = CryptoUtils.getCipherByProvider(getProvider(),req.getEncryptionTypeEnum(),req.getEncryptionModeEnum(),req.getPaddingType());

         encCipher.init(Cipher.DECRYPT_MODE, req.getKey(), new IvParameterSpec(req.getIv()));
         byte[] data = encCipher.doFinal(req.getData());
         result.setData(data);
         result.setErrorCode(NShieldErrorCode.NO_ERROR);
      } catch (IllegalBlockSizeException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.ILLEGAL_BLOCK_SIZE);
      } catch (BadPaddingException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.BAD_PADDING);
      } catch (NoSuchPaddingException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.NO_SUCH_PADDING);
      } catch (NoSuchAlgorithmException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.NO_SUCH_ALGORITHM);
      } catch (NoSuchProviderException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.NO_SUCH_PROVIDER);
      } catch (InvalidAlgorithmParameterException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.INVALID_ALGORITHM_PARAMETER);
      } catch (InvalidKeyException e) {
         result.setErrorMessage(e.getMessage());
         result.setErrorCode(NShieldErrorCode.INVALID_KEY);
      }

      return result;
   }

}
