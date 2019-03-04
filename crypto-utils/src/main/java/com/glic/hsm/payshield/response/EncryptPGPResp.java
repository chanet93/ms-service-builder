package com.glic.hsm.payshield.response;

/**
 * Created by erwine1 on 08/09/16.
 */
public class EncryptPGPResp  extends HsmCommandResp {

   private byte[] encrypted;

   public byte[] getEncrypted() {
      return encrypted;
   }

   public void setEncrypted(byte[] encrypted) {
      this.encrypted = encrypted;
   }
}
