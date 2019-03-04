package com.glic.hsm.payshield.response;

/**
 * @date 21/10/2014
 * @version $Id$
 */
public class DecryptRsaResp extends HsmCommandResp {

	private byte[] decryptedData;

	public DecryptRsaResp() {
		setCommandCode(DECRYPT_RSA_RESP);
	}

	public byte[] getDecryptedData() {
		return decryptedData;
	}

	public void setDecryptedData(byte[] decryptedData) {
		this.decryptedData = decryptedData;
	}
}
