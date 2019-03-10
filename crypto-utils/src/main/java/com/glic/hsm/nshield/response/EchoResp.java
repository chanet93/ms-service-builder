
package com.glic.hsm.nshield.response;

/**
 * @author erwine1
 * @date 15/12/2016
 */
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
