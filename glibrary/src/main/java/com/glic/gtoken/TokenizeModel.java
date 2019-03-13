package com.glic.gtoken;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.glic.gtoken.utils.TokenUtils;
import com.glic.gtoken.validators.ParametersValidator;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TokenizeModel {

   public static final String NONE = "NONE";

   private Integer renewTokenDays;

   private LocalDate tokenExpiryDate;

   private Boolean renewToken = false;

   @NotNull
   @Valid
   private CardDetails cardDetails;

   private String clientCHDToken;

   private String clientLPToken;

   @Transient
   public TwoState getTwoState() {
      if (Objects.nonNull(this.getRenewToken()) && this.getRenewToken()) {
         return TwoState.TRUE;
      } else {
         return TwoState.FALSE;
      }
   }


}
