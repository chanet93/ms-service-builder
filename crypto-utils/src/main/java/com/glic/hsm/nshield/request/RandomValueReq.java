package com.glic.hsm.nshield.request;


public class RandomValueReq extends NShieldCommandReq {

   private int bytesSize;

   /**
    * Set the size of bytesSize that you want to generate
    * @param bytesSize
    */
   public RandomValueReq(int bytesSize){
      setBytesSize(bytesSize);
   }

   public int getBytesSize() {
      return bytesSize;
   }

   public void setBytesSize(int bytesSize) {
      this.bytesSize = bytesSize;
   }
}
