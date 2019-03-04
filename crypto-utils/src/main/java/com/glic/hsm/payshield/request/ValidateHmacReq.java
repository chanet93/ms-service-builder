package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.HashAlgorithm;

public class ValidateHmacReq extends PayshieldCommandReq {

	private HashAlgorithm hashAlgorithm;

	private byte[] hmac;
	
	private byte[] hmacKey;

	private byte[] data;

	public ValidateHmacReq() {
		setCommandCode(PayshieldCommandReq.VALIDATE_HMAC_CMD);
	}

	public ValidateHmacReq(HashAlgorithm hashAlgorithm, byte[] hmac, byte[] hmacKey, byte[] data) {
		super();
		setCommandCode(PayshieldCommandReq.VALIDATE_HMAC_CMD);
		this.hashAlgorithm = hashAlgorithm;
		this.hmac = hmac;
		this.hmacKey = hmacKey;
		this.data = data;
	}

	public HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(HashAlgorithm hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public byte[] getHmac() {
		return hmac;
	}

	public void setHmac(byte[] hmac) {
		this.hmac= hmac;
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
