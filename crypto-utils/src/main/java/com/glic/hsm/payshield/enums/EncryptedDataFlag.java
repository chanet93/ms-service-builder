package com.glic.hsm.payshield.enums;

/**
 * Input message flags for the Thales commands
 *
 * @version $Id$
 * @date 30/9/2014
 */
public enum EncryptedDataFlag {

   BINARY("0"), HEX("1");

   private String commandValue;

   private EncryptedDataFlag(String commandValue) {
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