package com.glic.hostsimulator.common.exception;

public class MockServerException extends Exception {

   private static final long serialVersionUID = 1L;

   public MockServerException(String message) {
      super(message);
   }

   public MockServerException(String message, Exception e) {
      super(message, e);
   }

   public MockServerException(Exception e) {
      super(e);
   }

}
