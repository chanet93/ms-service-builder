package com.glic.gtoken;

import java.util.Arrays;
import java.util.List;

public enum TokenStatus {
   ACTIVE,
   DELETED,
   SUSPENDED;

   public List<TokenStatus> with(TokenStatus tokenStatus) {
      return Arrays.asList(this, tokenStatus);
   }

}
