package com.glic.hsm.nshield.response;

/**
 * @author erwine1
 */
public class EncryptResp extends NShieldCommandResp {

   private byte[] data;

   public void setData(byte[] data) {
      this.data = data;
   }

   public byte[] getData() {
      return data;
   }

}
