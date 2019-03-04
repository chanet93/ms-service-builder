package com.glic.hsm.payshield.simulator;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.DecryptReq;
import com.glic.hsm.payshield.response.DecryptResp;

public class SoftwareDecryptCommand implements Command<DecryptReq, DecryptResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareDecryptCommand.class.getName());

	@Override
	public DecryptResp execute(DecryptReq req) {

		DecryptResp result = new DecryptResp();

		if (req.getEncryptedData() == null || req.getKey() == null || req.getInitializationVector() == null) {
			result.setErrorCode(ErrorsDef.INVALID_INPUT_DATA);
			return result;
		}
		
		try {
			CryptoUtils crypto = new CryptoUtils();
			byte[] resultBytes = crypto.decryptData(req.getEncryptedData(), req.getKey(), req.getInitializationVector(), req.getEncryptionModeEnum(),req.getEncryptionAlgorithmEnum());

			result.setDecryptedData(resultBytes);
			result.setErrorCode(ErrorsDef.NO_ERROR);

		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			LOGGER.log(Level.SEVERE, "Exception thrown", e);
			result.setErrorCode(ErrorsDef.INTERNAL_ERROR);
		}

		return result;
	}

}
