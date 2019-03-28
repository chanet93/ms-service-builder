package com.glic.hostsimulator.tcp.model;

import java.util.Map;


public class TcpResponseDefinition {

   private String mti;

   private Map<Integer, String> isoFields;

   public String getMti() {
      return mti;
   }

   public void setMti(String mti) {
      this.mti = mti;
   }

   public Map<Integer, String> getIsoFields() {
      return isoFields;
   }

   public void setIsoFields(Map<Integer, String> isoFields) {
      this.isoFields = isoFields;
   }

}
