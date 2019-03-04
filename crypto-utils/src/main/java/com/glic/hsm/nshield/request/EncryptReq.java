package com.glic.hsm.nshield.request;

import java.security.Key;

import com.glic.hsm.nshield.enums.EncryptionModeEnum;
import com.glic.hsm.nshield.enums.EncryptionTypeEnum;
import com.glic.hsm.nshield.enums.PaddingTypeEnum;

/**
 * @author erwine1
 * This request will be used to create a new encrypt command.
 */
public class EncryptReq extends NShieldCommandReq {

   private EncryptionTypeEnum encryptionTypeEnum;

   private EncryptionModeEnum encryptionMode;

   private PaddingTypeEnum paddingType;

   private Key key;

   private byte[]data;

   private byte[] iv;

   /**
    *
    */
   public EncryptReq(){

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
   public EncryptReq(EncryptionTypeEnum type,EncryptionModeEnum mode,PaddingTypeEnum paddingType,byte[]data,byte[]iv,Key secretKey){
      setKey(secretKey);
      setData(data);
      setEncryptionMode(mode);
      setEncryptionTypeEnum(type);
      setPaddingType(paddingType);
      setIv(iv);
   }


   public EncryptionTypeEnum getEncryptionTypeEnum() {
      return encryptionTypeEnum;
   }

   public void setEncryptionTypeEnum(EncryptionTypeEnum encryptionTypeEnum) {
      this.encryptionTypeEnum = encryptionTypeEnum;
   }

   public EncryptionModeEnum getEncryptionMode() {
      return encryptionMode;
   }

   public void setEncryptionMode(EncryptionModeEnum encryptionMode) {
      this.encryptionMode = encryptionMode;
   }

   public PaddingTypeEnum getPaddingType() {
      return paddingType;
   }

   public void setPaddingType(PaddingTypeEnum paddingType) {
      this.paddingType = paddingType;
   }

   public Key getKey() {
      return key;
   }

   public void setKey(Key key) {
      this.key = key;
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
