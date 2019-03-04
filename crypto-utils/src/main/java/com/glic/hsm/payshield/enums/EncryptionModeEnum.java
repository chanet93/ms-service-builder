package com.glic.hsm.payshield.enums;

/**
 * Encryption Modes for the Thales commands
 *
 * @version $Id$
 * @date 30/9/2014
 */
public enum EncryptionModeEnum {

   ECB("00"), CBC("01"), CFB8("02"), CFB64("03"), RSA("01");

   private String commandValue;

   private EncryptionModeEnum(String commandValue) {
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