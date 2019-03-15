package com.glic.gtoken;

import com.glic.gtoken.validators.Required;

import lombok.Data;



@Required.List(value = { @Required(mode = Required.OR, fields = { "chdToken", "lpToken" }) })
@Data
public class TokenDeleteModel {

   private static final long serialVersionUID = -3475533698452650154L;

   private String lpToken;

   private String chdToken;

}
