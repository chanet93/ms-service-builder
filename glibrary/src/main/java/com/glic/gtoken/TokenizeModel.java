package com.glic.gtoken;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.glic.gtoken.utils.TokenUtils;
import com.glic.gtoken.validators.ParametersValidator;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TokenizeModel {

   public static final String NONE = "NONE";

   private static final String DEFAULT_DAY = "01";

   private Integer renewTokenDays;

   @ParametersValidator
   private String tokenExpiryDate;

   private Boolean renewToken = false;

   @NotNull
   @Valid
   private CardDetails cardDetails;

   @NotNull
   @ParametersValidator
   private String tokenType;

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

   //TODO MAKE THIS MORE PERFORMANT IS CALLED FROM MULTIPLE PLACES
   @Transient
   public String getPan() {
      String pre = "";
      if (Objects.nonNull(this.getCardDetails().getSequenceNumber())) {
         pre = String.valueOf(this.getCardDetails().getSequenceNumber());
      }
      return TokenUtils.constructPan(Pair.of(pre, this.getCardDetails().getCardNumber()));
   }

   //TODO This could be moved to a common class
   @Transient
   public Date getExp() throws ParseException {
      if (Objects.nonNull(this.getCardDetails().getExpiryYear()) && Objects.nonNull(this.getCardDetails().getExpiryMonth()) && Objects.nonNull(
            this.getCardDetails().getExpiryDay())) {
         return TokenUtils.getDateFromString(TokenUtils.YYYY_MM_DD,
               this.getCardDetails().getExpiryYear() + "-" + this.getCardDetails().getExpiryMonth() + "-" + this.getCardDetails().getExpiryDay());
      } else if (Objects.nonNull(this.getCardDetails().getExpiryYear()) && Objects.nonNull(this.getCardDetails().getExpiryMonth())) {
         return TokenUtils.getDateFromString(TokenUtils.YYYY_MM_DD,
               this.getCardDetails().getExpiryYear() + "-" + this.getCardDetails().getExpiryMonth() + "-" + DEFAULT_DAY);
      } else {
         return null;
      }
   }

   //TODO This could be moved to a common class
   @Transient
   public Date getTokenExpire() throws ParseException {
      return TokenUtils.getDateFromString(TokenUtils.YYYY_MM_DD, this.tokenExpiryDate);
   }

}
