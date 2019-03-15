package com.glic.hsm.nshield.response;

import javax.crypto.SecretKey;


public class GenerateHashKeyResp extends NShieldCommandResp {

   private SecretKey secretKey;

   /**
    * The secret key generated
    * @return
    */
   public SecretKey getSecretKey() {
      return secretKey;
   }

   public void setSecretKey(SecretKey secretKey) {
      this.secretKey = secretKey;
   }
}
