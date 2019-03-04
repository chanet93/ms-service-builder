package com.glic.hsm.payshield.simulator;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.GenerateHmacReq;
import com.glic.hsm.payshield.response.GenerateHmacResp;

public class SoftwareGenerateHmacCommand implements Command<GenerateHmacReq, GenerateHmacResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareGenerateHmacCommand.class.getName());
	
	@Override
	public GenerateHmacResp execute(GenerateHmacReq req) {

		GenerateHmacResp resp = new GenerateHmacResp();

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

			resp.setHmac(hmac);
			resp.setErrorCode(ErrorsDef.NO_ERROR);
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
