package com.glic.hsm.payshield.response;


/**
 * @version $Id$
 */
public class EncryptResp extends HsmCommandResp {
	
	private byte[] outputIv;
	
	private byte[] encryptedData;

	public EncryptResp() {
		setCommandCode(ENCRYPT_RESP);
	}

	public EncryptResp(String errorCode, byte[] encryptedData, byte[] outputIv) {
		setErrorCode(errorCode);
		this.encryptedData = encryptedData;
		this.outputIv = outputIv;
	}

	public byte[] getEncryptedData() {
		return encryptedData;
	}

	public byte[] getOutputIv() {
		return outputIv;
	}

	public void setEncryptedData(byte[] encryptedData) {
		this.encryptedData = encryptedData;
	}

	public void setOutputIv(byte[] outputIv) {
		this.outputIv = outputIv;
	}
}
