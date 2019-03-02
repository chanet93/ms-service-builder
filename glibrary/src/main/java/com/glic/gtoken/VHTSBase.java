package com.glic.gtoken;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class VHTSBase implements Serializable {

   private static final long serialVersionUID = 8968604949548899160L;

   private String vaid;

   private String eid;

}
