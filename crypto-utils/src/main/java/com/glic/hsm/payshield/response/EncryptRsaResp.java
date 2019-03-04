package com.glic.hsm.payshield.response;

/**
 * @date 22/10/2014
 * @version $Id$
 */
public class EncryptRsaResp extends HsmCommandResp {

	private byte[] encryptedData;

	public byte[] getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(byte[] encryptedData) {
		this.encryptedData = encryptedData;
	}

}
