package com.glic.payment.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BasicCardDetails {

   private String cardHolderName;

   @GlicLocalDate
   private LocalDate cardExpDate;

   private Integer panSequence;

}
