package com.glic.hsm.payshield.request;

public class GenerateKeyReq extends PayshieldCommandReq {

	public GenerateKeyReq() {
		setCommandCode(PayshieldCommandReq.GENERATE_KEY_CMD);
	}
}
