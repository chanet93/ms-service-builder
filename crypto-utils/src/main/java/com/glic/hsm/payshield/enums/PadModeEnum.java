package com.glic.hsm.payshield.enums;

/**
 * @author erwine1
 * This enum contains
 */
public enum PadModeEnum {

   PKCS1("01"), OAEP("02");

   private final String commandValue;

   private PadModeEnum(String commandValue) {
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
