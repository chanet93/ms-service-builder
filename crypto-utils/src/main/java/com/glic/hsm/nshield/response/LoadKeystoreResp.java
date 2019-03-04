package com.glic.hsm.nshield.response;

import java.security.KeyStore;

import com.glic.hsm.nshield.request.LoadKeystoreReq;

/**
 * Created by erwine1 on 19/12/16.
 */
public class LoadKeystoreResp extends NShieldCommandResp {


   private KeyStore keyStore;

   /**
    * Get the security world keystore from the sent alias in the {@link LoadKeystoreReq}
    * @return
    */
   public KeyStore getKeyStore() {
      return keyStore;
   }

   /**
    * Set the keystore from security world to be retrieved.
    * @param keyStore
    */
   public void setKeyStore(KeyStore keyStore) {
      this.keyStore = keyStore;
   }


}
