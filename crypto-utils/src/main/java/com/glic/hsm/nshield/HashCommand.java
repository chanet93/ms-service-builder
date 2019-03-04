package com.glic.hsm.nshield;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Objects;
import java.util.logging.Logger;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.HashReq;
import com.glic.hsm.nshield.response.HashResp;
import com.glic.hsm.payshield.ErrorsDef;

/**
 * @author erwine1
 */
public class HashCommand extends AbstractCommand implements Command<HashReq, HashResp> {

	private static final Logger LOGGER = Logger.getLogger(HashCommand.class.getName());
	
	@Override
	public HashResp execute(HashReq req) {
		
		HashResp result = new HashResp();

		try {

			MessageDigest digest;
			if(Objects.nonNull(getProvider())){
				digest = MessageDigest.getInstance(req.getHashTypeEnum().alias(),getProvider());
			}else{
				digest = MessageDigest.getInstance(req.getHashTypeEnum().alias());
			}

			digest.update(req.getMessageData());
			byte[] digestBytes = digest.digest();

			result.setHashValue(digestBytes);
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
