package com.glic.hsm.nshield.request;

import java.security.Key;

/**
 * Created by GabrielF3 on 14/03/2017.
 * This request is intended to store a new key into keystore for nshield.
 */
public class StoreKeystoreReq extends NShieldCommandReq {

   private String keystoreAlias;

   private String keyAlias;

   private Key key;

   public String getKeystoreAlias() {
      return keystoreAlias;
   }

   public void setKeystoreAlias(String keystoreAlias) {
      this.keystoreAlias = keystoreAlias;
   }

   public String getKeyAlias() {
      return keyAlias;
   }

   public void setKeyAlias(String keyAlias) {
      this.keyAlias = keyAlias;
   }

   public Key getKey() {
      return key;
   }

   public void setKey(Key key) {
      this.key = key;
   }
}
