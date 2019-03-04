package com.glic.hsm.nshield.request;

import com.glic.hsm.nshield.enums.HashTypeEnum;

/**
 * @author erwine1
 * This request will be used to create a hash request function such as SHA [224,256,384,512]
 */
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
