package com.glic.hsm.payshield.simulator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.crypto.InvalidCipherTextException;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.EncryptRsaReq;
import com.glic.hsm.payshield.response.EncryptRsaResp;

public class SoftwareEncryptRsaCommand implements Command<EncryptRsaReq, EncryptRsaResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareEncryptRsaCommand.class.getName());

	@Override
	public EncryptRsaResp execute(EncryptRsaReq req) {
		
		byte[] clearData = req.getClearData();
		byte[] publicKeyBytes = req.getPublicKey();

		EncryptRsaResp resp = new EncryptRsaResp();

		try {
			CryptoUtils crypto = new CryptoUtils();
			byte[] encryptedData = crypto.encryptRsa(clearData, publicKeyBytes);
			
			resp.setEncryptedData(encryptedData);
			resp.setErrorCode(ErrorsDef.NO_ERROR);
		} catch (InvalidCipherTextException | IOException  e) {
			resp.setErrorCode(ErrorsDef.INTERNAL_ERROR);
			LOGGER.log(Level.SEVERE, "Exception thrown while encrypting (RSA)", e);
		}

		return resp;
	}
}
