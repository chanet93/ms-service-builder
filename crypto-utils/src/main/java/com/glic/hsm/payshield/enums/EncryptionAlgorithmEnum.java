package com.glic.hsm.payshield.enums;

/**
 * Encryption Modes for the Thales commands
 *
 * @version $Id$
 * @date 30/9/2014
 */
public enum EncryptionAlgorithmEnum {

   AES("AES", false), DES("DES", true), TDES("DESede", true);

   private final String commandValue;

   private final boolean implementedToCallHSM;

   private EncryptionAlgorithmEnum(String commandValue, boolean hsmSupport) {
      this.commandValue = commandValue;
      this.implementedToCallHSM = hsmSupport;
   }

   /**
    * Get the thales command value for the enum
    *
    * @return thales command string
    */
   public String getCommandValue() {
      return commandValue;
   }

   public boolean isImplementedToCallHSM() {
      return implementedToCallHSM;
   }

}