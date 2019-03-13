package com.glic.gtoken;

import lombok.Data;

//@Required.List(value = { @Required(mode = Required.REQUIRED, field = "chdtoken") })
@Data
public class UnTokenModel {

   private String chdtoken;

   private TwoState tokenRenew;

   private Integer tokenRenewDays;

}
