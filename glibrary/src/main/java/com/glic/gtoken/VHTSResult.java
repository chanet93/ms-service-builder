package com.glic.gtoken;

import java.io.Serializable;

import com.glic.gtoken.VHTSBase;

/**
 * @author erwin
 * 18/04/2017
 */
public abstract class VHTSResult extends VHTSBase implements Serializable {

   private static final long serialVersionUID = -636362170558951387L;

   protected int resultCode;

   protected String resultText;

   public int getResultCode() {
      return resultCode;
   }

   public void setResultCode(int resultCode) {
      this.resultCode = resultCode;
   }

   public String getResultText() {
      return resultText;
   }

   public void setResultText(String resultText) {
      this.resultText = resultText;
   }

}
