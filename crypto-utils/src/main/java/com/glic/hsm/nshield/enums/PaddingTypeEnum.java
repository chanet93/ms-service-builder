package com.glic.hsm.nshield.enums;

/**
 * Created by erwine1 on 15/12/16.
 */
public enum PaddingTypeEnum {

   PKCS5("PKCS5Padding"),
   PKCS7("PKCS7Padding");

   private String padding;

   private PaddingTypeEnum(String padding) {
      this.padding = padding;
   }

   public String getPadding() {
      return padding;
   }
}
