package com.glic.hsm.nshield.response;

/**
 * @author erwine1
 */
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
