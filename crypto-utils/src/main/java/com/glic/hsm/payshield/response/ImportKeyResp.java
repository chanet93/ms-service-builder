package com.glic.hsm.payshield.response;

/**
 * Created by erwine1 on 27/04/17.
 */
public class ImportKeyResp extends HsmCommandResp {

   private String keyUnderLMK;

   private String keyCheckValue;

   public String getKeyUnderLMK() {
      return keyUnderLMK;
   }

   public void setKeyUnderLMK(String keyUnderLMK) {
      this.keyUnderLMK = keyUnderLMK;
   }

   public String getKeyCheckValue() {
      return keyCheckValue;
   }

   public void setKeyCheckValue(String keyCheckValue) {
      this.keyCheckValue = keyCheckValue;
   }

}
