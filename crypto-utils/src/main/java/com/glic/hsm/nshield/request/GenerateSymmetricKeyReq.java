package com.glic.hsm.nshield.request;

import com.glic.hsm.nshield.enums.EncryptionTypeEnum;


public class GenerateSymmetricKeyReq extends NShieldCommandReq {


   private EncryptionTypeEnum algorithmType;

   /**
    *
    * @param algorithmType
    */
   public GenerateSymmetricKeyReq(EncryptionTypeEnum algorithmType){
      setAlgorithmType(algorithmType);
   }

   /**
    *
    * @return
    */
   public EncryptionTypeEnum getAlgorithmType() {
      return algorithmType;
   }

   /**
    *
    * @param algorithmType
    */
   public void setAlgorithmType(EncryptionTypeEnum algorithmType) {
      this.algorithmType = algorithmType;
   }
}
