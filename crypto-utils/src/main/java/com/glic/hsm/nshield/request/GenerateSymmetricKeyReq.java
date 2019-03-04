package com.glic.hsm.nshield.request;

import com.glic.hsm.nshield.enums.EncryptionTypeEnum;

/**
 * @author erwine1
 * From the two types of key availables , this is used to create symetric keys such as AES , TDES and other symmetric algs.
 */
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
