package com.glic.hsm.payshield.enums;

/**
 * Output message flags for the Thales commands
 *
 * @version $Id$
 * @date 30/9/2014
 */
public enum DecryptedDataFlagEnum {

   BINARY("0"), HEX("1"), TEXT("2");

   private String commandValue;

   private DecryptedDataFlagEnum(String commandValue) {
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