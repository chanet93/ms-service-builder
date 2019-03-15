package com.glic.hsm.nshield;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Objects;
import java.util.logging.Logger;

import javax.crypto.Mac;

import org.bouncycastle.util.Arrays;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.ValidateHmacReq;
import com.glic.hsm.nshield.response.ValidateHmacResp;
import com.glic.hsm.payshield.ErrorsDef;


public class ValidateHmacCommand extends AbstractCommand implements Command<ValidateHmacReq, ValidateHmacResp> {

	private static final Logger LOGGER = Logger.getLogger(ValidateHmacCommand.class.getName());
	
	@Override
	public ValidateHmacResp execute(ValidateHmacReq req) {

		ValidateHmacResp result = new ValidateHmacResp();

		if (Objects.isNull(req.getHashTypeEnum())) {
			result.setErrorCode(ErrorsDef.INVALID_HASH_IDENTIFIER);
			return result;
		}

		try {

			Mac mac = CryptoUtils.getMacByProvider(getProvider(),req.getHashTypeEnum());
			mac.init(req.getKey());
			byte[] hmac = mac.doFinal(req.getData());

			boolean valid = Arrays.areEqual(hmac, req.getHmac());
			if (valid) {
				result.setErrorCode(ErrorsDef.NO_ERROR);
			} else {
				result.setErrorCode(ErrorsDef.HMAC_VERIFICATION_FAILURE);
			}
		} catch (NoSuchAlgorithmException e) {
			result.setErrorMessage(e.getMessage());
			result.setErrorCode(NShieldErrorCode.NO_SUCH_ALGORITHM);
		} catch (NoSuchProviderException e) {
			result.setErrorMessage(e.getMessage());
			result.setErrorCode(NShieldErrorCode.NO_SUCH_PROVIDER);
		} catch (InvalidKeyException e) {
			result.setErrorMessage(e.getMessage());
			result.setErrorCode(NShieldErrorCode.INVALID_KEY);
		}

		return result;
	}

}
