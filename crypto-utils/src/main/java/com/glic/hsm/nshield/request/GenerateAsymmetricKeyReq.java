package com.glic.hsm.nshield.request;

import com.glic.hsm.nshield.enums.EncryptionTypeEnum;

/**
 * @author erwine1
 * From the two types of key availables , this is used to create keypair
 */
public class GenerateAsymmetricKeyReq extends NShieldCommandReq {


   private EncryptionTypeEnum algorithmType;

   /**
    *
    * @param algorithmType
    */
   public GenerateAsymmetricKeyReq(EncryptionTypeEnum algorithmType){
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
