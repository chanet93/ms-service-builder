package com.glic.hsm.payshield.request;

/**
 * Created by erwine1 on 08/09/16.
 */
public class DecryptPGPReq extends PayshieldCommandReq {

   private byte[] encrypted;

   private byte[] secretKeys;

   private String passphrase;

   public void setEncrypted(byte[] encrypted) {
      this.encrypted = encrypted;
   }

   public byte[] getEncrypted() {
      return encrypted;
   }

   public void setSecretKeys(byte[] secretKeys) {
      this.secretKeys = secretKeys;
   }

   public byte[] getSecretKeys() {
      return secretKeys;
   }

   public void setPassphrase(String passphrase) {
      this.passphrase = passphrase;
   }

   public String getPassphrase() {
      return passphrase;
   }
}


