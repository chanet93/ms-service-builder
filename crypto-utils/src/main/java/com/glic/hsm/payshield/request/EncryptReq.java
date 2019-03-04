package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.enums.DecryptedDataFlagEnum;
import com.glic.hsm.payshield.enums.EncryptedDataFlag;
import com.glic.hsm.payshield.enums.EncryptionAlgorithmEnum;
import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.enums.KeyTypeEnum;

/**
 * @date 25/9/2014
 * @version $Id$
 */
public class EncryptReq extends PayshieldCommandReq {

    private String ksn;

    private String ksnDescriptor;

    private byte[] initializationVector;

    private EncryptionModeEnum encryptionModeEnum;

    private DecryptedDataFlagEnum inputFormatFlag;

    private EncryptedDataFlag outputFormatFlag;

    private byte[] data;

    private KeyTypeEnum keyTypeEnum;

    private byte[] key;

    private EncryptionAlgorithmEnum encryptionAlgorithmEnum;

    /**
     * Initialize the Decrypt Command with the necessary params and default
     * values for some items This constructor must be used for non-DUKPT
     * encryption
     *
     * @param iv initialize vector (almost always 0000000000)
     * @param encryptedKey
     * @param dataToEncrypt data to encrypt
     * @param encryptionAlgorithmEnum
     */
    public EncryptReq(byte[] iv, byte[] encryptedKey, byte[] dataToEncrypt, EncryptionAlgorithmEnum encryptionAlgorithmEnum) {
        // Empty fields as is a non-dukpt decryption
        this.ksn = "";
        this.ksnDescriptor = "";
        this.initializationVector = iv;
        setCommandCode(ENCRYPT_CMD);

        // default values
        this.encryptionAlgorithmEnum = encryptionAlgorithmEnum;
        this.encryptionModeEnum = EncryptionModeEnum.CBC;
        this.inputFormatFlag = DecryptedDataFlagEnum.TEXT;
        this.outputFormatFlag = EncryptedDataFlag.HEX;
        this.keyTypeEnum = KeyTypeEnum.DEK;
        this.key = encryptedKey;
        this.data = dataToEncrypt;
    }

    /**
     * Initialize the Decrypt Command with the necessary params and default
     * values for some items This constructor must be used for DUKPT encryption
     *
     * @param ksn - The KSN supplied by the PIN pad
     * @param ksnDescriptor - a three number String which is defined: -> Left
     * character: base derivation key identifier length. -> Middle character:
     * sub-key identifier key length (currently always 0). -> Right character:
     * device identifier length.
     * @param iv - initialize vector (almost always 0000000000)
     * @param encryptedBdk -
     * @param dataToEncrypt - data to encrypt
     */
    public EncryptReq(String ksn, String ksnDescriptor, byte[] iv, byte[] encryptedBdk, byte[] dataToEncrypt) {
        this.ksn = ksn;
        this.ksnDescriptor = ksnDescriptor;
        this.initializationVector = iv;
        setCommandCode(PayshieldCommandReq.DECRYPT_CMD);

        // default values
        this.encryptionModeEnum = EncryptionModeEnum.CBC;
        this.inputFormatFlag = DecryptedDataFlagEnum.TEXT;
        this.outputFormatFlag = EncryptedDataFlag.HEX;
        this.keyTypeEnum = KeyTypeEnum.BDK1;
        this.key = encryptedBdk;
        this.data = dataToEncrypt;
    }

    public EncryptionAlgorithmEnum getEncryptionAlgorithmEnum() {
        return encryptionAlgorithmEnum;
    }

    public void setEncryptionAlgorithmEnum(EncryptionAlgorithmEnum encryptionAlgorithmEnum) {
        this.encryptionAlgorithmEnum = encryptionAlgorithmEnum;
    }

    public byte[] getData() {
        return data;
    }

    public EncryptionModeEnum getEncryptionModeEnum() {
        return encryptionModeEnum;
    }

    public byte[] getInitializationVector() {
        return initializationVector;
    }

    public DecryptedDataFlagEnum getInputFlag() {
        return inputFormatFlag;
    }

    public byte[] getKey() {
        return key;
    }

    public KeyTypeEnum getkeyType() {
        return this.keyTypeEnum;
    }

    public KeyTypeEnum getKeyTypeEnum() {
        return keyTypeEnum;
    }

    public String getKsn() {
        return ksn;
    }

    public String getKsnDescriptor() {
        return ksnDescriptor;
    }

    public EncryptedDataFlag getOutputFlag() {
        return outputFormatFlag;
    }

    public void setData(byte[] encryptedData) {
        this.data = encryptedData;
    }

    /**
     * Describes the encryption mode: - ECB - CBC (requires IV) - CFB8 (requires
     * IV) - CFB64 (requires IV)
     *
     * @param mode
     */
    public void setEncryptionModeEnum(EncryptionModeEnum mode) {
        encryptionModeEnum = mode;
    }

    /**
     * The input IV, used in conjunction with the encryption Key. When
     * encrypting the first of a series of blocks, this initial IV should be set
     * by the caller – a typical initial IV is {00 00 00 00 00 00 00 00}. For
     * subsequent blocks, this value should be the IV returned from encrypting
     * the previous block. If using a DES/3-DES Key, the IV will be a 16 H
     * field. If using an AES Key, the IV will be a 32 H field.
     *
     * @param iv
     */
    public void setInitializationVector(byte[] iv) {
        this.initializationVector = iv;
    }

    /**
     * Describes the format of the input message (Binary, Hex-Encoded Binary,
     * Text).
     *
     * @param mode
     */
    public void setInputFlag(DecryptedDataFlagEnum flag) {
        inputFormatFlag = flag;
    }

    /**
     * The encryption Key, used in conjunction with the IV, if appropriate, to
     * encrypt the supplied Message.
     *
     * @param key
     */
    public void setKey(byte[] key) {
        this.key = key;
    }

    public void setKeyTypeEnum(KeyTypeEnum keyTypeEnum) {
        this.keyTypeEnum = keyTypeEnum;
    }

    /**
     * The KSN (key serial number) supplied by the PIN pad, including the
     * transaction counter. Only present if Key Type is a BDK.
     *
     * @param ksn
     */
    public void setKsn(String ksn) {
        this.ksn = ksn;
    }

    /**
     * The descriptor for the KSN (key serial number). Only present if Key Type
     * is a BDK.
     *
     * @param ksnDescriptor
     */
    public void setKsnDescriptor(String ksnDescriptor) {
        this.ksnDescriptor = ksnDescriptor;
    }

    /**
     * Describes the format of the output message: (Binary or Hex-Encoded
     * Binary).
     *
     * @param mode
     */
    public void setOutputFlag(EncryptedDataFlag flag) {
        this.outputFormatFlag = flag;
    }
}
