package com.glic.hsm.nshield.request;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import com.glic.hsm.nshield.enums.HashTypeEnum;


public class GenerateHmacReq extends NShieldCommandReq {

   private HashTypeEnum hashTypeEnum;

   private Key key;

   private byte[]data;

   /**
    *
    */
   public GenerateHmacReq(){

   }

   /**
    *
    * @param type
    * @param data
    * @param key
    */
   public GenerateHmacReq(HashTypeEnum type,byte[]data,Key key){
      setKey(key);
      setData(data);
      setHashTypeEnum(type);
   }

   public GenerateHmacReq(HashTypeEnum type,byte[]data,byte[] key){
      setKey(new SecretKeySpec(key,getHashTypeEnum().alias()));
      setData(data);
      setHashTypeEnum(type);
   }

   public HashTypeEnum getHashTypeEnum() {
      return hashTypeEnum;
   }

   public void setHashTypeEnum(HashTypeEnum hashTypeEnum) {
      this.hashTypeEnum = hashTypeEnum;
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
