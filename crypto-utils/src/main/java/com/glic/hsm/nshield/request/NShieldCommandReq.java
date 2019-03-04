package com.glic.hsm.nshield.request;

import com.glic.hsm.commons.CommandReq;

/**
 * Created by erwine1 on 12/12/16.
 * This is a generic class to declare as a nshield request family commands
 */
public class NShieldCommandReq extends CommandReq{

   protected String passphrase;

   /**
    * Returns the passphrase
    * @return
    */
   public char[] getPassphrase() {
      return (passphrase != null)?passphrase.toCharArray():null;
   }

   /**
    * Set a card passphrase, this is an optional field and depends on the configuration of security world (persistent or non-persistent), if you dont know just leave it in null
    * @param passphrase
    */
   public void setPassphrase(String passphrase) {
      this.passphrase = passphrase;
   }
}
