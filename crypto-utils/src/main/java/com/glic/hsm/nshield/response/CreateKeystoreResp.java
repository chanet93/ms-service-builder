package com.glic.hsm.nshield.response;

/**
 * Created by erwine1 on 19/12/16.
 */
public class CreateKeystoreResp extends NShieldCommandResp {

   private String keystoreAlias;

   public void setKeystoreAlias(String keystoreAlias) {
      this.keystoreAlias = keystoreAlias;
   }

   public String getKeystoreAlias() {
      return keystoreAlias;
   }
}
