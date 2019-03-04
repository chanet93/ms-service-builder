package com.glic.hsm.nshield.request;

import com.glic.hsm.nshield.enums.HashTypeEnum;

/**
 * @author erwine1
 * For the availables options to create keys, this will create hashing keys.
 */
public class GenerateHashingKeyReq extends NShieldCommandReq {


   private HashTypeEnum algorithmType;

   /**
    *
    * @param algorithmType
    */
   public GenerateHashingKeyReq(HashTypeEnum algorithmType){
      setAlgorithmType(algorithmType);
   }

   /**
    *
    * @return
    */
   public HashTypeEnum getAlgorithmType() {
      return algorithmType;
   }

   /**
    *
    * @param algorithmType
    */
   public void setAlgorithmType(HashTypeEnum algorithmType) {
      this.algorithmType = algorithmType;
   }
}
