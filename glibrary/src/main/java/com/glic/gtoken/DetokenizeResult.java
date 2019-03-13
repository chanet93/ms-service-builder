package com.glic.gtoken;

import java.time.LocalDate;
import java.util.Date;

import com.glic.payment.model.GlicLocalDate;

import lombok.Data;

@Data
public class DetokenizeResult extends VHTSResult {

   private static final long serialVersionUID = 7934823258312413040L;

   private String pan;

   private String cardholderData;

   private String tstatus;

   @GlicLocalDate
   private LocalDate texp;

   private String lpToken;

}
