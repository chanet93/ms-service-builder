package com.glic.hsm.nshield.response;

import javax.crypto.SecretKey;

/**
 * @author erwine1
 */
public class GenerateSymmetricKeyResp extends NShieldCommandResp {

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
