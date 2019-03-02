package com.glic.gtoken;

import com.glic.gtoken.validators.Required;

import lombok.Data;

/**
 * @author erwin
 * 14/11/2014
 */

@Required.List(value = { @Required(mode = Required.OR, fields = { "chdToken", "lpToken" }) })
@Data
public class TokenDeleteModel extends VHTSRequest {

   private static final long serialVersionUID = -3475533698452650154L;

   private String lpToken;

   private String chdToken;

}
