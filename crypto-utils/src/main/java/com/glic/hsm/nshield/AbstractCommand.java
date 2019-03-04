package com.glic.hsm.nshield;

/**
 * Created by erwine1 on 15/12/16.
 */
public abstract class AbstractCommand {

   public static final String NCIPHER_SWORLD_KEYSTORE_PROVIDER = "ncipher.sworld";

   protected String provider;

   public void setProvider(String provider) {
      this.provider = provider;
   }

   public String getProvider() {
      return provider;
   }
}



