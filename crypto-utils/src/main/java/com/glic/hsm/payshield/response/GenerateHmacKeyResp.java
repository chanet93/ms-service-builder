package com.glic.hsm.payshield.response;

public class GenerateHmacKeyResp extends HsmCommandResp {

	private byte[] hmacKey;

	public GenerateHmacKeyResp() {
		setCommandCode(GENERATE_HMAC_KEY_RESP);
	}

	public byte[] getHmacKey() {
		return hmacKey;
	}

	public void setHmacKey(byte[] hashKey) {
		this.hmacKey = hashKey;
	}
	
}
