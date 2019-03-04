package com.glic.hsm.commons;

/**
 * Created by erwine1 on 12/12/16.
 */
public abstract class CommandResp extends BaseCommand {

   private String errorCode;

   private String errorMessage;

   public String getErrorMessage() {
      return errorMessage;
   }

   public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
   }

   public String getErrorCode() {
      return errorCode;
   }

   public void setErrorCode(String errorCode) {
      this.errorCode = errorCode;
   }

}
