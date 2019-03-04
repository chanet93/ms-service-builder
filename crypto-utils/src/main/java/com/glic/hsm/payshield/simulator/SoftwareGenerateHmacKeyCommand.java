package com.glic.hsm.payshield.simulator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomUtils;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.GenerateHmacKeyReq;
import com.glic.hsm.payshield.response.GenerateHmacKeyResp;

public class SoftwareGenerateHmacKeyCommand implements Command<GenerateHmacKeyReq, GenerateHmacKeyResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareGenerateHmacKeyCommand.class.getName());
	
	@Override
	public GenerateHmacKeyResp execute(GenerateHmacKeyReq req) {

		GenerateHmacKeyResp resp = new GenerateHmacKeyResp();

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
			int keyLength = req.getHashAlgorithm().getHashBitsSize() / 8;
			resp.setHmacKey(RandomUtils.nextBytes(keyLength));
			resp.setErrorCode(ErrorsDef.NO_ERROR);
		} catch (Exception e) {
			resp.setErrorCode(ErrorsDef.INTERNAL_ERROR);
			LOGGER.log(Level.WARNING, "Unknown exception thrown", e);
		}

		return resp;
	}

}
