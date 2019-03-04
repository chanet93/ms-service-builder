package com.glic.hsm.payshield.simulator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.crypto.InvalidCipherTextException;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.DecryptRsaReq;
import com.glic.hsm.payshield.response.DecryptRsaResp;

/**
 * Created by ChuckNorris
 */
public class SoftwareDecryptRsaCommand implements Command<DecryptRsaReq, DecryptRsaResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareDecryptRsaCommand.class.getName());

	@Override
	public DecryptRsaResp execute(DecryptRsaReq req) {

		byte[] clearData = req.getData();
		byte[] privateKeyBytes = req.getPrivateKey();

		DecryptRsaResp resp = new DecryptRsaResp();
		
		try {
			CryptoUtils crypto = new CryptoUtils();
			byte[] decryptedData = crypto.decryptRsa(clearData, privateKeyBytes);
			
			resp.setDecryptedData(decryptedData);
			resp.setErrorCode(ErrorsDef.NO_ERROR);
		} catch (InvalidCipherTextException | IOException  e) {
			resp.setErrorCode(ErrorsDef.INTERNAL_ERROR);
			LOGGER.log(Level.SEVERE, "Exception thrown while encrypting (RSA)", e);
		}		

		return resp;
	}
}
