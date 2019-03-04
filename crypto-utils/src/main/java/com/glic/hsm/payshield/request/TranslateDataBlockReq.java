package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.enums.EncryptedDataFlag;
import com.glic.hsm.payshield.enums.EncryptionAlgorithmEnum;
import com.glic.hsm.payshield.enums.EncryptionModeEnum;

/**
 * Translate a block of data from encryption under one key, to encryption under
 * another key.
 *
 * @date 10/02/2014
 * @version $Id$
 */
public class TranslateDataBlockReq extends PayshieldCommandReq {

    private EncryptionModeEnum sourceEncryptionModeEnum;

    private EncryptionModeEnum destinationEncryptionModeEnum;

    private EncryptedDataFlag sourceFormat;

    private EncryptedDataFlag destinationFormat;

    private byte[] sourceKey;

    private byte[] destinationKey;

    private byte[] sourceIv;

    private byte[] destinationIv;

    private byte[] encryptedData;

    private EncryptionAlgorithmEnum encryptionAlgorithmEnum;

    public EncryptionAlgorithmEnum getEncryptionAlgorithmEnum() {
        return encryptionAlgorithmEnum;
    }

    public void setEncryptionAlgorithmEnum(EncryptionAlgorithmEnum encryptionAlgorithmEnum) {
        this.encryptionAlgorithmEnum = encryptionAlgorithmEnum;
    }

    public TranslateDataBlockReq() {
        setCommandCode(TRANSLATE_DATA_BLOCK_CMD);
    }

    public EncryptionModeEnum getSourceEncryptionModeEnum() {
        return sourceEncryptionModeEnum;
    }

    public void setSourceEncryptionModeEnum(EncryptionModeEnum sourceEncryptionModeEnum) {
        this.sourceEncryptionModeEnum = sourceEncryptionModeEnum;
    }

    public EncryptionModeEnum getDestinationEncryptionModeEnum() {
        return destinationEncryptionModeEnum;
    }

    public void setDestinationEncryptionModeEnum(EncryptionModeEnum destinationEncryptionModeEnum) {
        this.destinationEncryptionModeEnum = destinationEncryptionModeEnum;
    }

    public EncryptedDataFlag getSourceFormat() {
        return sourceFormat;
    }

    public void setSourceFormat(EncryptedDataFlag sourceFormat) {
        this.sourceFormat = sourceFormat;
    }

    public EncryptedDataFlag getDestinationFormat() {
        return destinationFormat;
    }

    public void setDestinationFormat(EncryptedDataFlag destinationFormat) {
        this.destinationFormat = destinationFormat;
    }

    public byte[] getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(byte[] sourceKey) {
        this.sourceKey = sourceKey;
    }

    public byte[] getDestinationKey() {
        return destinationKey;
    }

    public void setDestinationKey(byte[] destinationKey) {
        this.destinationKey = destinationKey;
    }

    public byte[] getSourceIv() {
        return sourceIv;
    }

    public void setSourceIv(byte[] sourceIv) {
        this.sourceIv = sourceIv;
    }

    public byte[] getDestinationIv() {
        return destinationIv;
    }

    public void setDestinationIv(byte[] destinationIv) {
        this.destinationIv = destinationIv;
    }

    public byte[] getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(byte[] encryptedData) {
        this.encryptedData = encryptedData;
    }
}
