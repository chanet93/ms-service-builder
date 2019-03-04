package com.glic.hsm.commons;

/**
 * Created by erwine1 on 20/12/16.
 * This is a generic class that indicates all subclases belongs to a command hierarchy.
 */
public abstract class BaseCommand {

   private String header;

   public String getHeader() {
      return header;
   }

   public void setHeader(String header) {
      this.header = header;
   }


}
