package com.glic.gtoken;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.glic.gtoken.validators.ParametersValidator;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({ "sequenceNumber", "cardholderName", "expiryMonth", "cardholderData", "expiryDay", "expiryYear", "cardNumber" })
@Data
public class CardDetailsUpdate {

   @JsonProperty("cardholderData")
   @ParametersValidator
   private String cardholderData;

   @JsonProperty("cardNumber")
   @ParametersValidator
   private String cardNumber;

}
