package com.glic.hsm.payshield.response;

/**
 * @date 10/02/2015
 * @version $Id$
 */
public class TranslateDataBlockResp extends HsmCommandResp {

	private byte[] sourceIv;
	
	private byte[] destinationIv;
	
	private byte[] encryptedData;
	
	public TranslateDataBlockResp() {
		setCommandCode(TRANSLATE_DATA_BLOCK_RESP);
	}

	public byte[] getSourceIv() {
		return sourceIv;
	}

	public void setSourceIv(byte[] sourceIv) {
		this.sourceIv = sourceIv;
	}

	public byte[] getDestinationIv() {
		return destinationIv;
	}

	public void setDestinationIv(byte[] destinationIv) {
		this.destinationIv = destinationIv;
	}

	public byte[] getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(byte[] encryptedData) {
		this.encryptedData = encryptedData;
	}
}
