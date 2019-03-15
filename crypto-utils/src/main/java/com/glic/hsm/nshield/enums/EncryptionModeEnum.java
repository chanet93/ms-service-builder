package com.glic.hsm.nshield.enums;


public enum EncryptionModeEnum {

   CBC("CBC",16);

   private String alias;
   private int blockSize;

   private EncryptionModeEnum(String alias,int blockSize) {
      this.blockSize = blockSize;
      this.alias = alias;
   }

   public String getAlias() {
      return alias;
   }

   public int getBlockSize() {
      return blockSize;
   }
}