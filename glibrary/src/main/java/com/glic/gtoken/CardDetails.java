package com.glic.gtoken;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.glic.gtoken.validators.ParametersValidator;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CardDetails {

   @ParametersValidator
   private String cardholderData;

   @NotNull
   @ParametersValidator
   private String cardNumber;

}
