package com.glic.hsm.payshield.response;

public class GenerateHmacResp extends HsmCommandResp {

	private byte[] hmac;

	public GenerateHmacResp() {
		setCommandCode(GENERATE_HMAC_RESP);
	}

	public byte[] getHmac() {
		return hmac;
	}

	public void setHmac(byte[] hmac) {
		this.hmac = hmac;
	}
	
}
