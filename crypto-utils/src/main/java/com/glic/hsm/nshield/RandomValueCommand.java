package com.glic.hsm.nshield;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.enums.RandomAlgorythmEnum;
import com.glic.hsm.nshield.request.RandomValueReq;
import com.glic.hsm.nshield.response.RandomValueResp;
import com.glic.hsm.payshield.ErrorsDef;


public class RandomValueCommand extends AbstractCommand implements Command<RandomValueReq, RandomValueResp> {

	@Override
	public RandomValueResp execute(RandomValueReq req) {

		RandomValueResp result = new RandomValueResp();
		try {

			byte[] randomBytes = new byte[req.getBytesSize()];
			SecureRandom secureRandom  = CryptoUtils.getSecureRandomByProvider(getProvider(),RandomAlgorythmEnum.SHA1PRNG);
			secureRandom.nextBytes(randomBytes);
			result.setBytes(randomBytes);
			result.setErrorCode(ErrorsDef.NO_ERROR);

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
