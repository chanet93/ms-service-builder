package com.glic.payment.constants;

public enum EResponseCode {

   TERMINAL_NOT_FOUND("10001"),
   CARD_ROUTE_NOT_MATCH("10002"),
   GENERIC_ERROR("10003"),
   RELATED_TRX_NOT_FOUND("10004"),
   CARD_NOT_DETERMINED("10005"),
   COMPLETION_TO_NOT_APPROVED_TRX("10006"),
   VOID_TO_NOT_APPROVED_TRX("10007"),
   REFUND_TO_NOT_APPROVED_TRX("10008"),
   REVERSE_TO_NOT_APPROVED_TRX("10009"),
   ERROR_CALL_GW("10010"),
   ACQUIRER_DECLINE("10011"),
   ACQUIRER_MODULE_ERROR("10012");

   private final String code;

   EResponseCode(String code) {
      this.code = code;
   }

   public String getCode() {
      return this.code;
   }

   public String getResourceKey() {
      return "responsecode." + this.code;
   }
}
