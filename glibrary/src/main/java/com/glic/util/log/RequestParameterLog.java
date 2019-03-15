package com.glic.util.log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.glic.util.log.converter.LogConverter;


public class RequestParameterLog {

   private static final Map<String, LogConverter> FIELDS_CONVERSION = new HashMap<>();

   static {
      // Common to more than one submodule
      FIELDS_CONVERSION.put("trxId", Converter.DEF);
      FIELDS_CONVERSION.put("originalTrxId", Converter.DEF);
      FIELDS_CONVERSION.put("uniqueId", Converter.DEF);
      FIELDS_CONVERSION.put("acquirerid", Converter.DEF);
      FIELDS_CONVERSION.put("routerAcquirerId", Converter.DEF);
      FIELDS_CONVERSION.put("terminalId", Converter.DEF);
      FIELDS_CONVERSION.put("terminalSerialNumber", Converter.DEF);
      FIELDS_CONVERSION.put("currency", Converter.DEF);
      FIELDS_CONVERSION.put("pan", Converter.CARD);
      FIELDS_CONVERSION.put("maskedPan", Converter.DEF);
      FIELDS_CONVERSION.put("cardExpDate", Converter.HIDE);
      FIELDS_CONVERSION.put("track1", Converter.TRACK);
      FIELDS_CONVERSION.put("track2", Converter.TRACK);
      FIELDS_CONVERSION.put("cvv2", Converter.HIDE);
      FIELDS_CONVERSION.put("chName", Converter.HIDE);
      FIELDS_CONVERSION.put("gatewayResponseCode", Converter.DEF);
      FIELDS_CONVERSION.put("gatewayResponseText", Converter.DEF);
      FIELDS_CONVERSION.put("trxStatus", Converter.DEF);
      FIELDS_CONVERSION.put("trxType", Converter.DEF);
      FIELDS_CONVERSION.put("online", Converter.DEF);
      FIELDS_CONVERSION.put("dtRequest", Converter.DEF);
      FIELDS_CONVERSION.put("dtResponse", Converter.DEF);
      FIELDS_CONVERSION.put("dtAuthorization", Converter.DEF);
      FIELDS_CONVERSION.put("dtSettlement", Converter.DEF);
      FIELDS_CONVERSION.put("dtRequestBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("dtResponseBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("dtAuthorizationBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("dtSettlementBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("acqResponseCode", Converter.DEF);
      FIELDS_CONVERSION.put("acqResponseText", Converter.DEF);
      FIELDS_CONVERSION.put("acqAuthCoded", Converter.DEF);
      FIELDS_CONVERSION.put("acqStan", Converter.DEF);
      FIELDS_CONVERSION.put("acqRrn", Converter.DEF);
      FIELDS_CONVERSION.put("acquirerTerminalId", Converter.DEF);
      FIELDS_CONVERSION.put("cardToken", Converter.HIDE);
      FIELDS_CONVERSION.put("cardTokenOneWay", Converter.DEF);

      FIELDS_CONVERSION.put("relatedTrxData.trxId", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.originalTrxId", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.uniqueId", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acquirerid", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.routerAcquirerId", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.terminalId", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.terminalSerialNumber", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.currency", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.pan", Converter.CARD);
      FIELDS_CONVERSION.put("relatedTrxData.maskedPan", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.cardExpDate", Converter.HIDE);
      FIELDS_CONVERSION.put("relatedTrxData.track1", Converter.TRACK);
      FIELDS_CONVERSION.put("relatedTrxData.track2", Converter.TRACK);
      FIELDS_CONVERSION.put("relatedTrxData.cvv2", Converter.HIDE);
      FIELDS_CONVERSION.put("relatedTrxData.chName", Converter.HIDE);
      FIELDS_CONVERSION.put("relatedTrxData.gatewayResponseCode", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.gatewayResponseText", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.trxStatus", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.trxType", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.online", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtRequest", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtResponse", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtAuthorization", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtSettlement", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtRequestBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtResponseBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtAuthorizationBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.dtSettlementBranchTz", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acqResponseCode", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acqResponseText", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acqAuthCoded", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acqStan", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acqRrn", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acquirerTerminalId", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.acquirerMerchantId", Converter.DEF);
      FIELDS_CONVERSION.put("relatedTrxData.cardToken", Converter.HIDE);
      FIELDS_CONVERSION.put("relatedTrxData.cardTokenOneWay", Converter.DEF);

   }

   private RequestParameterLog() {
      // Hide constructor
   }

   public static LogConverter getConverter(String fieldName) {
      LogConverter ret = FIELDS_CONVERSION.get(fieldName);
      if (Objects.isNull(ret)) {
         ret = FIELDS_CONVERSION.get(StringUtils.lowerCase(fieldName));
      }
      return ret;
   }

}
