package com.glic.hsm.payshield.enums;

/**
 * Key Types for the Thales commands
 *
 * @version $Id$
 * @date 30/9/2014
 */
public enum KeyTypeEnum {

   BDK1("009"),
   BDK2("609"),
   BDK3("809"),
   ZEK("00A"),
   ZPK("001"),
   ZAK("008"),
   DEK("00B"),
   TEK("30B"),
   RSA_PKI("3400");

   private String commandValue;

   KeyTypeEnum(String commandValue) {
      this.commandValue = commandValue;
   }

   /**
    * Get the thales command value for the enum
    *
    * @return thales command string
    */
   public String getCommandValue() {
      return commandValue;
   }
}