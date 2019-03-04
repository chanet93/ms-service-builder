package com.glic.hsm.nshield;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Objects;
import java.util.logging.Logger;

import javax.crypto.Mac;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.GenerateHmacReq;
import com.glic.hsm.nshield.response.GenerateHmacResp;
import com.glic.hsm.payshield.ErrorsDef;

/**
 * @author erwine1
 */
public class GenerateHmacCommand extends AbstractCommand implements Command<GenerateHmacReq, GenerateHmacResp> {

	private static final Logger LOGGER = Logger.getLogger(GenerateHmacCommand.class.getName());
	
	@Override
	public GenerateHmacResp execute(GenerateHmacReq req) {

		GenerateHmacResp result = new GenerateHmacResp();

		if (Objects.isNull(req.getHashTypeEnum())) {
			result.setErrorCode(ErrorsDef.INVALID_HASH_IDENTIFIER);
			return result;
		}

		try {
			Mac mac = CryptoUtils.getMacByProvider(getProvider(),req.getHashTypeEnum());
			mac.init(req.getKey());
			byte[] hmac = mac.doFinal(req.getData());
			result.setErrorCode(NShieldErrorCode.NO_ERROR);
			result.setHmac(hmac);
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
