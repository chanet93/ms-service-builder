package com.glic.hsm.nshield.enums;

/**
 * Created by erwine1 on 15/12/16.
 * This enum contains the supported random algorithms.
 */
public enum RandomAlgorythmEnum {

   SHA1PRNG("SHA1PRNG"),
   RNG("RNG");

   private String alias;

   private RandomAlgorythmEnum(String alias) {
      this.alias = alias;
   }

   public String getAlias() {
      return alias;
   }
}
