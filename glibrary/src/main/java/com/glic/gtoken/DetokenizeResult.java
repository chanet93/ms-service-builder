package com.glic.gtoken;

import java.util.Date;

import lombok.Data;

@Data
public class DetokenizeResult extends VHTSResult {

   private static final long serialVersionUID = 7934823258312413040L;

   private String pan;

   private String panIv;

   private Date exp;

   private String cname;

   private String caddr;

   private String cpcode;

   private String ctype;

   private String tstatus;

   private Date texp;

   private String lpToken;

}
