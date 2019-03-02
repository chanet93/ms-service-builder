package com.glic.gtoken;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glic.gtoken.validators.ParametersValidator;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CardDetails {

   private Integer expiryYear;

   private Integer sequenceNumber;

   @ParametersValidator
   private String cardholderName;

   private Integer expiryMonth;

   private String cardHolderAddress;

   private String cardHolderPostalCode;

   @ParametersValidator
   private String cardholderData;

   @JsonProperty("expiryDay")
   private Integer expiryDay;

   @NotNull
   @ParametersValidator
   private String cardNumber;

}
