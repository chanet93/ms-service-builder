package com.glic.hsm.nshield.response;

/**
 * @author erwine1
 */
public class HashResp extends NShieldCommandResp {

   private byte[] hashValue;

   public byte[] getHashValue() {
      return hashValue;
   }

   public void setHashValue(byte[] hashValue) {
      this.hashValue = hashValue;
   }

}
