package com.glic.hsm.payshield.response;

/**
 * Created by erwine1 on 08/09/16.
 */
public class DecryptPGPResp extends HsmCommandResp {


   private byte[] decrypted;

   public byte[] getDecrypted() {
      return decrypted;
   }

   public void setDecrypted(byte[] decrypted) {
      this.decrypted = decrypted;
   }
}
