package com.glic.hsm.nshield.enums;


public enum HashTypeEnum {


	HMAC_MD5("HmacMD5",128),
	HMAC_SHA1("HmacSHA1", 160),
	HMAC_SHA_224("HmacSHA224", 224),
	HMAC_SHA_256("HmacSHA256", 256),
	HMAC_SHA_384("HmacSHA384", 384),
	HMAC_SHA_512("HmacSHA512", 512),
	SHA_1("SHA1",160),
	MD5("MD5",128),
	SHA_224("SHA224",224),
	SHA_256("SHA256",256),
	SHA_384("SHA384",384),
	SHA_512("SHA512",512);

	private String alias;

	private int size;

	private HashTypeEnum(String algorithmId, int size) {
		this.alias = algorithmId;
		this.size = size;
	}

	public String alias() {
		return alias;
	}


	public int size() {
		return size;
	}
}