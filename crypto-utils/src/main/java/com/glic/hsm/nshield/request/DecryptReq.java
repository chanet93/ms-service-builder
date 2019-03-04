package com.glic.hsm.nshield.request;

import java.security.Key;

import com.glic.hsm.nshield.enums.EncryptionModeEnum;
import com.glic.hsm.nshield.enums.EncryptionTypeEnum;
import com.glic.hsm.nshield.enums.PaddingTypeEnum;

/**
 * @author erwine1
 * This request will be used to create a new decrypt command.
 */
public final class DecryptReq extends NShieldCommandReq {

   private EncryptionTypeEnum encryptionTypeEnum;

   private EncryptionModeEnum encryptionModeEnum;

   private PaddingTypeEnum paddingType;

   private Key key;

   private byte[]data;

   private byte[] iv;

   /**
    *
    */
   public DecryptReq(){

   }

   /**
    *
    * @param type
    * @param mode
    * @param paddingType
    * @param data
    * @param iv
    * @param secretKey
    */
   public DecryptReq(EncryptionTypeEnum type,EncryptionModeEnum mode,PaddingTypeEnum paddingType,byte[]data,byte[]iv,Key secretKey){
      setKey(secretKey);
      setData(data);
      setEncryptionModeEnum(mode);
      setEncryptionTypeEnum(type);
      setPaddingType(paddingType);
      setIv(iv);

   }

   public void setPaddingType(PaddingTypeEnum paddingType) {
      this.paddingType = paddingType;
   }

   public PaddingTypeEnum getPaddingType() {
      return paddingType;
   }

   public void setKey(Key key) {
      this.key = key;
   }

   public Key getKey() {
      return key;
   }

   public EncryptionModeEnum getEncryptionModeEnum() {
      return encryptionModeEnum;
   }

   public void setEncryptionModeEnum(EncryptionModeEnum encryptionModeEnum) {
      this.encryptionModeEnum = encryptionModeEnum;
   }

   public EncryptionTypeEnum getEncryptionTypeEnum() {
      return encryptionTypeEnum;
   }

   public void setEncryptionTypeEnum(EncryptionTypeEnum encryptionTypeEnum) {
      this.encryptionTypeEnum = encryptionTypeEnum;
   }

   public byte[] getData() {
      return data;
   }

   public void setData(byte[] data) {
      this.data = data;
   }

   public byte[] getIv() {
      return iv;
   }

   public void setIv(byte[] iv) {
      this.iv = iv;
   }
}
