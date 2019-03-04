package com.glic.hsm.payshield.response;

public class GenerateKeyResp extends HsmCommandResp {

	private byte[] key;
	
	private String keyCheckValue;
	
	public GenerateKeyResp() {
		setCommandCode(GENERATE_KEY_RESP);
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public String getKeyCheckValue() {
		return keyCheckValue;
	}

	public void setKeyCheckValue(String keyCheckValue) {
		this.keyCheckValue = keyCheckValue;
	}
}
