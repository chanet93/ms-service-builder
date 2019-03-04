package com.glic.hsm.payshield.simulator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.HashReq;
import com.glic.hsm.payshield.response.HashResp;

public class SoftwareHashCommand implements Command<HashReq, HashResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareHashCommand.class.getName());
	
	@Override
	public HashResp execute(HashReq req) {
		
		HashResp resp = new HashResp();
		
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(req.getHashAlgorithm().getHashAlgorithm());
			digest.update(req.getMessageData());
			byte[] digestBytes = digest.digest();
			
			resp.setHashValue(digestBytes);
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
