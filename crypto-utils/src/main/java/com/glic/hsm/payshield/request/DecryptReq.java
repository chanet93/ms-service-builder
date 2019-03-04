package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.enums.DecryptedDataFlagEnum;
import com.glic.hsm.payshield.enums.EncryptedDataFlag;
import com.glic.hsm.payshield.enums.EncryptionAlgorithmEnum;
import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.enums.KeyTypeEnum;

/**
 * Represents a Decrypt data Thales Command, Can be a DUKPT decryption or use
 * tak as well TODO: for the moment the delimiter and LMK selection not
 * implemented.
 *
 * @date 26/9/2014
 * @version $Id$
 * @author ErnestoQ1
 */
public final class DecryptReq extends PayshieldCommandReq {

    private byte[] initializationVector;

    private EncryptionModeEnum encryptionModeEnum;

    private EncryptedDataFlag inputFormatFlag;

    private DecryptedDataFlagEnum outputFormatFlag;

    private byte[] encryptedData;

    private KeyTypeEnum keyTypeEnum;

    private byte[] key;

    private EncryptionAlgorithmEnum encryptionAlgorithmEnum;

    /**
     * Initialize the Decrypt Command with the necessary params and default
     * values for some items This constructor must be used for non-DUKPT
     * encryption
     *
     * @param iv - initialize vector (almost always 0000000000)
     * @param key
     * @param encryptedData - data to dencrypt
     * @param encryptionAlgorithmEnum - algorithm use to decryptdata
     */
    public DecryptReq(byte[] iv, byte[] key, byte[] encryptedData, EncryptionAlgorithmEnum encryptionAlgorithmEnum) {

        this.setInitializationVector(iv);
        setCommandCode(PayshieldCommandReq.DECRYPT_CMD);

        // default values
        this.encryptionAlgorithmEnum = encryptionAlgorithmEnum;
        this.encryptionModeEnum = EncryptionModeEnum.CBC;
        this.inputFormatFlag = EncryptedDataFlag.HEX;
        this.outputFormatFlag = DecryptedDataFlagEnum.HEX;
        this.keyTypeEnum = KeyTypeEnum.DEK;
        this.key = key;
        this.encryptedData = encryptedData;
    }

    public EncryptionAlgorithmEnum getEncryptionAlgorithmEnum() {
        return encryptionAlgorithmEnum;
    }

    public void setEncryptionAlgorithmEnum(EncryptionAlgorithmEnum encryptionAlgorithmEnum) {
        this.encryptionAlgorithmEnum = encryptionAlgorithmEnum;
    }

    public EncryptionModeEnum getDecryptMode() {
        return encryptionModeEnum;
    }

    public byte[] getEncryptedData() {
        return encryptedData;
    }

    public EncryptionModeEnum getEncryptionModeEnum() {
        return encryptionModeEnum;
    }

    public byte[] getInitializationVector() {
        return initializationVector;
    }

    public EncryptedDataFlag getInputFlag() {
        return inputFormatFlag;
    }

    public byte[] getKey() {
        return key;
    }

    public KeyTypeEnum getKeyTypeEnum() {
        return keyTypeEnum;
    }

    public String getMessageLength() {
        return getMessageLength();
    }

    public DecryptedDataFlagEnum getOutputFlag() {
        return outputFormatFlag;
    }

    public void setDecryptMode(EncryptionModeEnum mode) {
        encryptionModeEnum = mode;
    }

    public void setEncryptedData(byte[] encryptedData) {
        this.encryptedData = encryptedData;
    }

    public void setEncryptionModeEnum(EncryptionModeEnum encryptionModeEnum) {
        this.encryptionModeEnum = encryptionModeEnum;
    }

    public void setInitializationVector(byte[] initializeVector) {
        this.initializationVector = initializeVector;
    }

    public void setInputFlag(EncryptedDataFlag flag) {
        inputFormatFlag = flag;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public void setKeyTypeEnum(KeyTypeEnum keyTypeEnum) {
        this.keyTypeEnum = keyTypeEnum;
    }

    public void setOutputFlag(DecryptedDataFlagEnum flag) {
        this.outputFormatFlag = flag;
    }
}
