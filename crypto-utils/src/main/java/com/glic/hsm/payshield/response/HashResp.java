package com.glic.hsm.payshield.response;

/**
 * @date 30/9/2014
 * @version $Id$
 */
public class HashResp extends HsmCommandResp {
	private byte[] hashValue;

	public HashResp() {
		setCommandCode(HASH_RESP);
	}

	public byte[] getHashValue() {
		return hashValue;
	}

	public void setHashValue(byte[] hashValue) {
		this.hashValue = hashValue;
	}

}
