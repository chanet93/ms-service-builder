package com.glic.hostsimulator.tcp.model;

import java.util.Map;

public class TcpDefaultResponseDefinition {

   private Map<Integer, String> isoFields;

   public Map<Integer, String> getIsoFields() {
      return isoFields;
   }

   public void setIsoFields(Map<Integer, String> isoFields) {
      this.isoFields = isoFields;
   }
}
