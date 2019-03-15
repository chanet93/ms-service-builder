package com.glic.hsm.nshield.response;


public class GenerateHmacResp extends NShieldCommandResp {

   private byte[] hmac;

   public GenerateHmacResp() {

   }

   public byte[] getHmac() {
      return hmac;
   }

   public void setHmac(byte[] hmac) {
      this.hmac = hmac;
   }




}
