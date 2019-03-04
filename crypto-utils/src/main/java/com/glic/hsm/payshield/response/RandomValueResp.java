package com.glic.hsm.payshield.response;

/**
 * @date 15/10/2014
 * @version $Id$
 */
public class RandomValueResp extends HsmCommandResp {
	
	private byte[] randomBytes;

	public RandomValueResp() {
		setCommandCode(HsmCommandResp.RANDOM_VALUE_RESP);
	}
	
	public byte[] getRandomBytes() {
		return randomBytes;
	}

	public void setRandomBytes(byte[] randomBytes) {
		this.randomBytes = randomBytes;
	}

}
