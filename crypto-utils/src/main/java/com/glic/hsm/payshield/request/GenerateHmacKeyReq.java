package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.HashAlgorithm;

public class GenerateHmacKeyReq extends PayshieldCommandReq {

	private HashAlgorithm hashAlgorithm;

	public GenerateHmacKeyReq() {
		setCommandCode(PayshieldCommandReq.GENERATE_HMAC_KEY_CMD);
	}

	public GenerateHmacKeyReq(HashAlgorithm hashAlgorithm) {
		super();
		setCommandCode(PayshieldCommandReq.GENERATE_HMAC_KEY_CMD);
		this.hashAlgorithm = hashAlgorithm;
	}

	public HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(HashAlgorithm hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

}
