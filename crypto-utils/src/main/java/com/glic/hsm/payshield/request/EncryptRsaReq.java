package com.glic.hsm.payshield.request;

/**
 * @date 22/10/2014
 * @version $Id$
 */
public class EncryptRsaReq extends PayshieldCommandReq {

    private byte[] clearData;

    private byte[] publicKey;

    public EncryptRsaReq() {
        setCommandCode(ENCRYPT_RSA_CMD);
    }

    public EncryptRsaReq(byte[] clearData, byte[] publicKey) {
        super();
        setCommandCode(ENCRYPT_RSA_CMD);
        this.clearData = clearData;
        this.publicKey = publicKey;
    }

    public byte[] getClearData() {
        return clearData;
    }

    public void setClearData(byte[] dataToEncrypt) {
        this.clearData = dataToEncrypt;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

}
