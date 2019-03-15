package com.glic.hsm.nshield.request;

import com.glic.hsm.nshield.enums.HashTypeEnum;


public class HashReq extends NShieldCommandReq {

   private HashTypeEnum hashTypeEnum;
   private byte[] messageData;

   public HashReq() {

   }

   /**
    * @param type Algorithm to use in hashing
    * @param messageData String data to hash
    */
   public HashReq(HashTypeEnum type, byte[] messageData) {
      setHashTypeEnum(type);
      setMessageData(messageData);
   }

   public HashTypeEnum getHashTypeEnum() {
      return hashTypeEnum;
   }

   public void setHashTypeEnum(HashTypeEnum hashTypeEnum) {
      this.hashTypeEnum = hashTypeEnum;
   }

   public byte[] getMessageData() {
      return messageData;
   }

   public void setMessageData(byte[] messageData) {
      this.messageData = messageData;
   }
}
