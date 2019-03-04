package com.glic.hsm.payshield;

/**
 * Hash Types for the Thales commands
 * 
 * @date 30/9/2014
 * @version $Id$
 */
public enum HashAlgorithm {
	
	SHA_1(1, 160, "SHA-1"),
	MD5(2, 128, "MD5"),
	ISO101182(3, 512),
	SHA_224(5, 224, "SHA-224"),
	SHA_256(6, 256, "SHA-256"),
	SHA_384(7, 384, "SHA-384"),
	SHA_512(8, 512, "SHA-512");

	private int algorithmId;

	private int hashBitsSize;

	private String hashAlgorithm;

	private HashAlgorithm(int algorithmId, int hashBitsSize) {
		this.algorithmId = algorithmId;
		this.hashBitsSize = hashBitsSize;
	}

	private HashAlgorithm(int algorithmId, int hashBitsSize, String digestValue) {
		this.algorithmId = algorithmId;
		this.hashBitsSize = hashBitsSize;
		this.hashAlgorithm = digestValue;
	}

	/**
	 * Get the thales command value for the enum
	 * 
	 * @return thales command string
	 */
	public int getAlgorithmId() {
		return algorithmId;
	}

	/**
	 * Get the java message digest value for the algorithm
	 * 
	 * @return
	 */
	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public int getHashBitsSize() {
		return hashBitsSize;
	}
}