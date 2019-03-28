package com.glic.hostsimulator.tcp.model;

import java.util.Map;

import wiremock.com.fasterxml.jackson.annotation.JsonProperty;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;


public class TcpRequestDefinition {

   private static final ObjectMapper ow = new ObjectMapper();

   private String mti;

   private int priority;

   public TcpRequestDefinition() {
      priority = 0;
   }

   @JsonProperty("isoFields")
   private Map<Integer, String> matchingFields;

   @Override
   public String toString() {
      try {
         return ow.writeValueAsString(this);
      } catch (JsonProcessingException e) {
         return "";
      }
   }

   public String getMti() {
      return mti;
   }

   public void setMti(String mti) {
      this.mti = mti;
   }

   public int getPriority() {
      return priority;
   }

   public void setPriority(int priority) {
      this.priority = priority;
   }

   public Map<Integer, String> getMatchingFields() {
      return matchingFields;
   }

   public void setMatchingFields(Map<Integer, String> matchingFields) {
      this.matchingFields = matchingFields;
   }

}
