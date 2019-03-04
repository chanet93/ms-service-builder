package com.glic.hsm.payshield.request;

/**
 * Created by erwine1 on 08/09/16.
 */
public class EncryptPGPReq extends PayshieldCommandReq {

   private byte[] publicKey;

   private byte[] data;

   public void setPublicKey(byte[] publicKey) {
      this.publicKey = publicKey;
   }

   public byte[] getPublicKey() {
      return publicKey;
   }

   public void setData(byte[] data) {
      this.data = data;
   }

   public byte[] getData() {
      return data;
   }
}
