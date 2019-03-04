package com.glic.hsm.nshield.request;

/**
 * Created by erwine1 on 19/12/16.
 * This command will be used when you want to retrieve the keystore for nShield.
 */
public class LoadKeystoreReq extends NShieldCommandReq {

   private String keystoreAlias;

   /**
    * This is the keystore alias, this value is used to refer to which keystore do you want to load.
    * @param keystoreAlias
    */
   public void setKeystoreAlias(String keystoreAlias) {
      this.keystoreAlias = keystoreAlias;
   }

   /**
    * The alias for previosly generated keystore, in nShield the keystore is stored into the kmdata/local directory.
    *
    * @return
    */
   public String getKeystoreAlias() {
      return keystoreAlias;
   }
}


