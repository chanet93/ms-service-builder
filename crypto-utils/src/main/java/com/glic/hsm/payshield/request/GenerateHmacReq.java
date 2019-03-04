package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.HashAlgorithm;

public class GenerateHmacReq extends PayshieldCommandReq {

	private HashAlgorithm hashAlgorithm;

	private byte[] hmacKey;

	private byte[] data;

	public GenerateHmacReq() {
		setCommandCode(PayshieldCommandReq.GENERATE_HMAC_CMD);
	}

	public GenerateHmacReq(HashAlgorithm hashAlgorithm, byte[] hmacKey, byte[] messageData) {
		super();
		setCommandCode(PayshieldCommandReq.GENERATE_HMAC_CMD);
		this.hashAlgorithm = hashAlgorithm;
		this.hmacKey = hmacKey;
		this.data = messageData;
	}

	public HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(HashAlgorithm hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public byte[] getHmacKey() {
		return hmacKey;
	}

	public void setHmacKey(byte[] hmacKey) {
		this.hmacKey = hmacKey;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] messageData) {
		this.data = messageData;
	}
}
