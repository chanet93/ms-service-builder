package com.glic.hsm.payshield.request;

/**
 * Created by erwine1 on 08/09/16.
 */
public class GeneratePGPKeyPairReq extends PayshieldCommandReq {


   private int keySize;
   private String userId;
   private String password;

   public int getKeySize() {
      return keySize;
   }

   public String getUserId() {
      return userId;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setKeySize(int keySize) {
      this.keySize = keySize;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }
}
