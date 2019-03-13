package com.glic.gtoken;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public abstract class VHTSResult extends VHTSBase implements Serializable {

   private static final long serialVersionUID = -636362170558951387L;

   protected int resultCode;

   protected String resultText;

}
