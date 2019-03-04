package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.HashAlgorithm;

/**
 * @date 29/9/2014
 * @version $Id$
 */
public class HashReq extends PayshieldCommandReq {
    private HashAlgorithm hashAlgorithm;
    private byte[] messageData;
    
    public HashReq() {
    	setCommandCode(PayshieldCommandReq.HASH_CMD);
	}

    /**
     * @param type Algorithm to use in hashing
     * @param messageData String data to hash
     */
    public HashReq(HashAlgorithm type, byte[] messageData) {
        setCommandCode(PayshieldCommandReq.HASH_CMD);
        this.hashAlgorithm = type;
        this.messageData = messageData;
    }

	public HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(HashAlgorithm type) {
		this.hashAlgorithm = type;
	}

	public byte[] getMessageData() {
		return messageData;
	}

	public void setMessageData(byte[] messageData) {
		this.messageData = messageData;
	}

}
