package com.glic.hsm.payshield.simulator;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.Arrays;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.ValidateHmacReq;
import com.glic.hsm.payshield.response.ValidateHmacResp;

public class SoftwareValidateHmacCommand implements Command<ValidateHmacReq, ValidateHmacResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareValidateHmacCommand.class.getName());
	
	@Override
	public ValidateHmacResp execute(ValidateHmacReq req) {

		ValidateHmacResp resp = new ValidateHmacResp();

		if (req.getHashAlgorithm() == null) {
			resp.setErrorCode(ErrorsDef.INVALID_HASH_IDENTIFIER);
			return resp;
		}
		
		CryptoUtils cryptoUtils = new CryptoUtils();
		String hmacAlgorithm = cryptoUtils.getHmacAlgorithm(req.getHashAlgorithm());

		if (hmacAlgorithm == null) {
			resp.setErrorCode(ErrorsDef.INVALID_HASH_IDENTIFIER);
			return resp;
		}

		try {
			SecretKeySpec keySpec = new SecretKeySpec(req.getHmacKey(), hmacAlgorithm);
			Mac mac = Mac.getInstance(hmacAlgorithm);
			mac.init(keySpec);
			byte[] hmac = mac.doFinal(req.getData());

			boolean valid = Arrays.areEqual(hmac, req.getHmac());
			if (valid) {
				resp.setErrorCode(ErrorsDef.NO_ERROR);
			} else {
				resp.setErrorCode(ErrorsDef.HMAC_VERIFICATION_FAILURE);
			}
			
		} catch (NoSuchAlgorithmException e) {
			resp.setErrorCode(ErrorsDef.INVALID_HASH_IDENTIFIER);
			LOGGER.log(Level.WARNING, "No such algorithm", e);
		} catch (Exception e) {
			resp.setErrorCode(ErrorsDef.INTERNAL_ERROR);
			LOGGER.log(Level.WARNING, "Unknown exception thrown", e);
		}

		return resp;
	}

}
