package com.glic.gtoken;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "resultCode", "resultText", "vaid", "eid" })
@JsonPropertyOrder({ "irreversibleToken", "tokenStatus", "tokenExpiryDate", "tokenApplication", "tokenEntity", "cardholderToken" })
public class TokenizeResponse extends VHTSResult {

   @JsonProperty("tokenStatus")
   private String tokenStatus;

   @JsonProperty("tokenExpiryDate")
   private Date tokenExpiryDate;

   @JsonProperty("tokenApplication")
   private String tokenApplication;

   @JsonProperty("irreversibleToken")
   private String lossPreventionToken;

   @JsonProperty("tokenEntity")
   private String tokenEntity;

   @JsonProperty("cardholderToken")
   private String chdToken;

   @JsonProperty("tokenStatus")
   public String getTokenStatus() {
      return tokenStatus;
   }

   @JsonProperty("tokenStatus")
   public void setTokenStatus(String tokenStatus) {
      this.tokenStatus = tokenStatus;
   }

   @JsonProperty("tokenExpiryDate")
   public Date getTokenExpiryDate() {
      return tokenExpiryDate;
   }

   @JsonProperty("tokenExpiryDate")
   public void setTokenExpiryDate(Date tokenExpiryDate) {
      this.tokenExpiryDate = tokenExpiryDate;
   }

   @JsonProperty("tokenApplication")
   public String getTokenApplication() {
      return tokenApplication;
   }

   @JsonProperty("tokenApplication")
   public void setTokenApplication(String tokenApplication) {
      this.tokenApplication = tokenApplication;
   }

   @JsonProperty("irreversibleToken")
   public String getLossPreventionToken() {
      return lossPreventionToken;
   }

   @JsonProperty("irreversibleToken")
   public void setLossPreventionToken(String lossPreventionToken) {
      this.lossPreventionToken = lossPreventionToken;
   }

   @JsonProperty("tokenEntity")
   public String getTokenEntity() {
      return tokenEntity;
   }

   @JsonProperty("tokenEntity")
   public void setTokenEntity(String tokenEntity) {
      this.tokenEntity = tokenEntity;
   }

   @JsonProperty("cardholderToken")
   public String getChdToken() {
      return chdToken;
   }

   @JsonProperty("cardholderToken")
   public void setChdToken(String chdToken) {
      this.chdToken = chdToken;
   }

}
