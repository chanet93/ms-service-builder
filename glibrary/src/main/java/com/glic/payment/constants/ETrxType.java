package com.glic.payment.constants;


public enum ETrxType {

   SALE(true),
   PRE_AUTH(true),
   COMPLETION(false),
   VOID(false),
   REFUND(false),
   IMPLICIT_REVERSE(false),
   TOKENIZE(false);

   private final boolean requiresCard;

   ETrxType(boolean requiresCard) {
      this.requiresCard = requiresCard;
   }

   public boolean requiresCard() {
      return this.requiresCard;

   }
}
