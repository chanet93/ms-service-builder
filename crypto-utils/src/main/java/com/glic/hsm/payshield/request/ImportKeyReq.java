package com.glic.hsm.payshield.request;

import com.glic.hsm.payshield.enums.KeyTypeEnum;

/**
 * Created by erwine1 on 27/04/17.
 */
public class ImportKeyReq extends PayshieldCommandReq {

   private KeyTypeEnum keyTypeEnum;

   private String encryptedKey;

   private String keyCheckValue;

   private String zmk;

   public ImportKeyReq() {
      setCommandCode(IMPORT_KEY_CMD);
   }

   public void setKeyTypeEnum(KeyTypeEnum keyTypeEnum) {
      this.keyTypeEnum = keyTypeEnum;
   }

   public KeyTypeEnum getKeyTypeEnum() {
      return keyTypeEnum;
   }

   public String getEncryptedKey() {
      return encryptedKey;
   }

   public void setEncryptedKey(String encryptedKey) {
      this.encryptedKey = encryptedKey;
   }

   public String getKeyCheckValue() {
      return keyCheckValue;
   }

   public void setKeyCheckValue(String keyCheckValue) {
      this.keyCheckValue = keyCheckValue;
   }

   public String getZmk() {
      return zmk;
   }

   public void setZmk(String zmk) {
      this.zmk = zmk;
   }
}
