package com.glic.hsm.payshield.request;

import static com.glic.hsm.payshield.enums.PadModeEnum.PKCS1;

import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.enums.KeyTypeEnum;
import com.glic.hsm.payshield.enums.MaskGenFunctionHashEnum;
import com.glic.hsm.payshield.enums.PadModeEnum;

/**
 * Rsa decryption function TODO: for the moment we only support software
 * decryption
 *
 * @date 21/10/2014
 * @version $Id$
 */
public class DecryptRsaReq extends PayshieldCommandReq {

    private byte[] data;

    private byte[] privateKey;

    private String encId;

    private PadModeEnum mode;

    private EncryptionModeEnum encryptMode;

    // only present if mode is OAEP
    private String maskFuction;

    private MaskGenFunctionHashEnum maskFunctionHash;

    private String encodingParametersLenght;

    private String encodingParameters;

    // Numeric 4 digis PPVV where PP is the lmk key pair used and vv is the variant
    private KeyTypeEnum keyTypeEnum;

    private String dataBlockLength;

    private String dataBlock;

    // Flago 00 to 20 if the private key is stored in thales, 99 if key specified in command
    private String privateKeyFlag;

    private String privateKeyLenght;

    // Only present if flag 99
    private String privateKeyText;

    // Thales encription parameters
    private String hsmKeyParameters;

    /**
     * Constructor, set some basics data
     */
    public DecryptRsaReq() {
        // allways rsa
        setEncId("02");
        setMaskFuction("01");
        this.keyTypeEnum = KeyTypeEnum.RSA_PKI;
        this.encryptMode = EncryptionModeEnum.RSA;
        this.hsmKeyParameters = ";99";
        setCommandCode(DECRYPT_RSA_CMD);
        setMode(PKCS1);
    }

    public DecryptRsaReq(byte[] data, byte[] privateKey) {
        super();
        this.data = data;
        this.privateKey = privateKey;
        this.encryptMode = EncryptionModeEnum.RSA;
        this.keyTypeEnum = KeyTypeEnum.RSA_PKI;
        this.hsmKeyParameters = ";99";
        setCommandCode(DECRYPT_RSA_CMD);
        setMode(PKCS1);
    }

    /**
     * @return
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKeyBytes) {
        this.privateKey = privateKeyBytes;
    }

    public PadModeEnum getMode() {
        return mode;
    }

    public void setMode(PadModeEnum mode) {
        this.mode = mode;
    }

    public MaskGenFunctionHashEnum getMaskFunctionHash() {
        return maskFunctionHash;
    }

    public void setMaskFunctionHash(MaskGenFunctionHashEnum maskFunctionHash) {
        this.maskFunctionHash = maskFunctionHash;
    }

    public String getEncodingParametersLenght() {
        return encodingParametersLenght;
    }

    public void setEncodingParametersLenght(String encodingParametersLenght) {
        this.encodingParametersLenght = encodingParametersLenght;
    }

    public String getEncodingParameters() {
        return encodingParameters;
    }

    public void setEncodingParameters(String encodingParameters) {
        this.encodingParameters = encodingParameters;
    }

    public KeyTypeEnum getKeyTypeEnum() {
        return keyTypeEnum;
    }

    public void setKeyTypeEnum(KeyTypeEnum keyTypeEnum) {
        this.keyTypeEnum = keyTypeEnum;
    }

    public String getDataBlockLength() {
        return dataBlockLength;
    }

    public void setDataBlockLength(String dataBlockLength) {
        this.dataBlockLength = dataBlockLength;
    }

    public String getDataBlock() {
        return dataBlock;
    }

    public void setDataBlock(String dataBlock) {
        this.dataBlock = dataBlock;
    }

    public String getPrivateKeyFlag() {
        return privateKeyFlag;
    }

    public void setPrivateKeyFlag(String privateKeyFlag) {
        this.privateKeyFlag = privateKeyFlag;
    }

    public String getPrivateKeyLenght() {
        return privateKeyLenght;
    }

    public void setPrivateKeyLenght(String privateKeyLenght) {
        this.privateKeyLenght = privateKeyLenght;
    }

    public String getHsmKeyParameters() {
        return hsmKeyParameters;
    }

    public void setHsmKeyParameters(String hsmKeyParameters) {
        this.hsmKeyParameters = hsmKeyParameters;
    }

    public EncryptionModeEnum getEncryptMode() {
        return encryptMode;
    }

    public void setEncryptMode(EncryptionModeEnum encryptMode) {
        this.encryptMode = encryptMode;
    }

	public String getEncId() {
		return encId;
	}

	public void setEncId(String encId) {
		this.encId = encId;
	}

	public String getMaskFuction() {
		return maskFuction;
	}

	public void setMaskFuction(String maskFuction) {
		this.maskFuction = maskFuction;
	}

	public String getPrivateKeyText() {
		return privateKeyText;
	}

	public void setPrivateKeyText(String privateKeyText) {
		this.privateKeyText = privateKeyText;
	}

}
