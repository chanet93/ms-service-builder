package com.glic.hsm.payshield.exception;

public class PaddingException extends Exception {

   private static final long serialVersionUID = 9099564867354315720L;

   public PaddingException() {
      super();
   }

   public PaddingException(String message) {
      super(message);
   }

   public PaddingException(Throwable cause) {
      super(cause);
   }

   public PaddingException(String message, Throwable cause) {
      super(message, cause);
   }

   public PaddingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

}