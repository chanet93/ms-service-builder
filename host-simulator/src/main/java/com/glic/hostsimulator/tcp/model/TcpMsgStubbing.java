package com.glic.hostsimulator.tcp.model;

import wiremock.com.fasterxml.jackson.annotation.JsonProperty;


public class TcpMsgStubbing {

   @JsonProperty("request")
   TcpRequestDefinition requestDef;

   @JsonProperty("response")
   TcpResponseDefinition responseDef;

   public TcpRequestDefinition getRequestDef() {
      return requestDef;
   }

   public void setRequestDef(TcpRequestDefinition requestDef) {
      this.requestDef = requestDef;
   }

   public TcpResponseDefinition getResponseDef() {
      return responseDef;
   }

   public void setResponseDef(TcpResponseDefinition responseDef) {
      this.responseDef = responseDef;
   }
}
