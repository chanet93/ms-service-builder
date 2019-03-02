package com.glic.gtoken;

import com.glic.gtoken.validators.Required;

/**
 * @author erwin
 * 16/10/2014
 */
@Required.List(value = { @Required(mode = Required.REQUIRED, field = "chdtoken") })
public class UnTokenModel extends VHTSRequest {

   private static final long serialVersionUID = 2439367984985789090L;

   private String chdtoken;

   private TwoState tokenRenew;

   private Integer tokenRenewDays;

   public String getChdtoken() {
      return chdtoken;
   }

   public void setChdtoken(String chdtoken) {
      this.chdtoken = chdtoken;
   }

   public TwoState getTokenRenew() {
      return tokenRenew;
   }

   public void setTokenRenew(TwoState tokenRenew) {
      this.tokenRenew = tokenRenew;
   }

   public Integer getTokenRenewDays() {
      return tokenRenewDays;
   }

   public void setTokenRenewDays(Integer tokenRenewDays) {
      this.tokenRenewDays = tokenRenewDays;
   }

}
