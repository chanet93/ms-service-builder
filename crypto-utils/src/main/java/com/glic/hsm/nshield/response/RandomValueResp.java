package com.glic.hsm.nshield.response;


public class RandomValueResp extends NShieldCommandResp {

   private byte[] bytes;

   public byte[] getBytes() {
      return bytes;
   }

   public void setBytes(byte[] bytes) {
      this.bytes = bytes;
   }
}
