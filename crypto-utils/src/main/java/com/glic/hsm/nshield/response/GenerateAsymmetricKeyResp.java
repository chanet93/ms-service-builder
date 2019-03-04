package com.glic.hsm.nshield.response;

import java.security.KeyPair;

/**
 * @author erwine1
 */
public class GenerateAsymmetricKeyResp extends NShieldCommandResp {

   private KeyPair keyPair;

   public KeyPair getKeyPair() {
      return keyPair;
   }

   public void setKeyPair(KeyPair keyPair) {
      this.keyPair = keyPair;
   }
}
