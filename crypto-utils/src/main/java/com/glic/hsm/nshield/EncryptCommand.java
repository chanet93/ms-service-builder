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
import com.glic.hsm.nshield.request.EncryptReq;
import com.glic.hsm.nshield.response.EncryptResp;
import com.glic.hsm.payshield.ErrorsDef;

/**
 * @author erwine1
 */
public class EncryptCommand extends AbstractCommand implements Command<EncryptReq, EncryptResp> {

   private static final Logger LOGGER = Logger.getLogger(EncryptCommand.class.getName());

   @Override
   public EncryptResp execute(EncryptReq req) {

      EncryptResp result = new EncryptResp();

      if (ArrayUtils.isEmpty(req.getData())) {
         result.setErrorCode(NShieldErrorCode.EMPTY_OR_INVALID_DATA);
         return result;
      }

      try {

         Cipher encCipher  = CryptoUtils.getCipherByProvider(getProvider(),req.getEncryptionTypeEnum(),req.getEncryptionMode(),req.getPaddingType());
         encCipher.init(Cipher.ENCRYPT_MODE, req.getKey(), new IvParameterSpec(req.getIv()));
         byte[] encryptedData = encCipher.doFinal(req.getData());
         result.setData(encryptedData);
         result.setErrorCode(ErrorsDef.NO_ERROR);
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
