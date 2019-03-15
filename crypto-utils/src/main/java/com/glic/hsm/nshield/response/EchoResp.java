
package com.glic.hsm.nshield.response;


public class EchoResp extends NShieldCommandResp {

   private String messageData;

   public EchoResp() {

   }

   public String getMessageData() {
      return messageData;
   }

   public void setMessageData(String messageData) {
      this.messageData = messageData;
   }

}
