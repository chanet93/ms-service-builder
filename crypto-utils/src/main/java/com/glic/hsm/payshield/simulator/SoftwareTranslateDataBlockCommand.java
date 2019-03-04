package com.glic.hsm.payshield.simulator;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.TranslateDataBlockReq;
import com.glic.hsm.payshield.response.TranslateDataBlockResp;

public class SoftwareTranslateDataBlockCommand implements Command<TranslateDataBlockReq, TranslateDataBlockResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareTranslateDataBlockCommand.class.getName());

	@Override
	public TranslateDataBlockResp execute(TranslateDataBlockReq req) {
		
		TranslateDataBlockResp result = new TranslateDataBlockResp();
		
		if (req.getSourceKey() == null || req.getSourceIv() == null || 
			req.getDestinationKey() == null || req.getDestinationIv() == null ||
			req.getEncryptedData() == null) {
			result.setErrorCode(ErrorsDef.INVALID_INPUT_DATA);
			return result;
		}
		
		try {
			CryptoUtils crypto = new CryptoUtils();
			
			byte[] clearBytes = crypto.decryptData(req.getEncryptedData(), req.getSourceKey(), req.getSourceIv(), req.getSourceEncryptionModeEnum(),req.getEncryptionAlgorithmEnum());

			byte[] resultBytes = crypto.encryptData(clearBytes, req.getDestinationKey(), req.getDestinationIv(), req.getDestinationEncryptionModeEnum(),req.getEncryptionAlgorithmEnum());

			result.setEncryptedData(resultBytes);
			result.setErrorCode(ErrorsDef.NO_ERROR);

		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			LOGGER.log(Level.SEVERE, "Exception thrown", e);
			result.setErrorCode(ErrorsDef.INTERNAL_ERROR);
		}
		
		return result;
	}
}
