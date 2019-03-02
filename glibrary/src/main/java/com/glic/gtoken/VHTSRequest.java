package com.glic.gtoken;

import java.io.Serializable;

import lombok.Data;

/**
 * @author erwin
 * @date 16/10/2014
 */
@Data
public abstract class VHTSRequest extends VHTSBase implements Serializable {

   private static final long serialVersionUID = 8531802220029484596L;

   private String user;

   private String vpwd;

}
