package com.glic.hsm.nshield.request;

import java.security.Key;

import com.glic.hsm.nshield.enums.HashTypeEnum;


public class ValidateHmacReq extends NShieldCommandReq {

   private HashTypeEnum hashTypeEnum;

   private byte[] hmac;

   private Key key;

   private byte[] data;

   /**
    *
    * @param hashTypeEnum
    * @param hmac
    * @param data
    * @param key
    */
   public ValidateHmacReq(HashTypeEnum hashTypeEnum, byte[] hmac, byte[] data, Key key) {
      this.hashTypeEnum = hashTypeEnum;
      this.hmac = hmac;
      this.key = key;
      this.data = data;
   }

   public HashTypeEnum getHashTypeEnum() {
      return hashTypeEnum;
   }

   public void setHashTypeEnum(HashTypeEnum hashTypeEnum) {
      this.hashTypeEnum = hashTypeEnum;
   }

   public byte[] getHmac() {
      return hmac;
   }

   public void setHmac(byte[] hmac) {
      this.hmac = hmac;
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
}
