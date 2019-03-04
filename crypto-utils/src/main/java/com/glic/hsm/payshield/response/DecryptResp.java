package com.glic.hsm.payshield.response;


/**
 * @version $Id$
 */
public class DecryptResp extends HsmCommandResp {

	private byte[] decryptedData;

	private byte[] outputIv;

	public DecryptResp() {
		setCommandCode(DECRYPT_RESP);
	}

	public byte[] getDecryptedData() {
		return decryptedData;
	}

	public byte[] getOutputIv() {
		return outputIv;
	}

	public void setDecryptedData(byte[] decryptedData) {
		this.decryptedData = decryptedData;
	}

	public void setOutputIv(byte[] outputIv) {
		this.outputIv = outputIv;
	}
}
