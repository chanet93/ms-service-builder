package com.glic.hsm.nshield.response;


public class DecryptResp extends NShieldCommandResp {


   private byte[] data;

   public void setData(byte[] data) {
      this.data = data;
   }

   public byte[] getData() {
      return data;
   }
}
