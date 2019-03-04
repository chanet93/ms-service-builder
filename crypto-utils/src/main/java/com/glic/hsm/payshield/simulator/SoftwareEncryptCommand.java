package com.glic.hsm.payshield.simulator;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.EncryptReq;
import com.glic.hsm.payshield.response.EncryptResp;

public class SoftwareEncryptCommand implements Command<EncryptReq, EncryptResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareEncryptCommand.class.getName());

	@Override
	public EncryptResp execute(EncryptReq req) {

		EncryptResp result = new EncryptResp();

		if (req.getData() == null || req.getKey() == null || req.getInitializationVector() == null) {
			result.setErrorCode(ErrorsDef.INVALID_INPUT_DATA);
			return result;
		}
		
		try {
			CryptoUtils crypto = new CryptoUtils();
			byte[] resultBytes = crypto.encryptData(req.getData(), req.getKey(), req.getInitializationVector(), req.getEncryptionModeEnum(),req.getEncryptionAlgorithmEnum());

			result.setEncryptedData(resultBytes);
			result.setErrorCode(ErrorsDef.NO_ERROR);
						
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			LOGGER.log(Level.SEVERE, "Exception thrown", e);
			result.setErrorCode(ErrorsDef.INTERNAL_ERROR);
		}
		
		return result;
	}

}
