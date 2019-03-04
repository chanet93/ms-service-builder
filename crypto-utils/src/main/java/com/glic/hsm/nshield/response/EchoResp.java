/*
 * Copyright (c) 2013. VeriFone Uruguay S.A. All Rights Reserved.
 * 
 * This Module contains Propietary Information of
 * VeriFone Uruguay S.A. and should be treated as Confidential.
 * 
 * This Module is provided "AS IS" Without any warranties implicit
 * or explicit.
 * 
 * The use of this Module implies the acceptance of all the terms
 * and conditions of the "User License".
 */
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
