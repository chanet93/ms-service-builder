package com.glic.hsm.nshield.enums;


public enum EncryptionTypeEnum {


	AES_128("Rijndael",128),
	AES_192("Rijndael",192),
	AES_256("Rijndael",256),
	DES2("DES2",128),
	TDES("DESede",192),
	RSA_2048("RSA", 2048);

	private String alias;

	private int size;

	private EncryptionTypeEnum(String algorithmId, int size) {
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