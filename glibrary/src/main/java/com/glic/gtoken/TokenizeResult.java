package com.glic.gtoken;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class TokenizeResult extends VHTSResult implements Serializable {

   private static final long serialVersionUID = -7028776864765433925L;

   private String lptoken;

   private String chdtoken;

   private LocalDate tokenExpirationDate;

   private TokenStatus tokenStatus;

}
