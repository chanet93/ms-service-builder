package com.glic.util.log;

import java.util.Objects;

import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glic.payment.model.BasicTrx;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrxLog {

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   public static String getTrxLog(BasicTrx trx, boolean response) {
      if (Objects.isNull(trx)) {
         return "Empty payload";
      }
      StringBuilder sb = new StringBuilder();
      if (response) {
         sb.append(">> OUTCOME ").append(trx.getUniqueId()).append(" >>\n");
      } else {
         sb.append("<< INCOME ").append(trx.getUniqueId()).append(" <<\n");
      }
      try {
         String content = OBJECT_MAPPER.writeValueAsString(trx);
         JsonLogger.parseJson(content, sb, 1);
      } catch (JsonProcessingException e) {
         log.error("Error parsing message for log", e);

      }

      return sb.toString();
   }

   public static String getTrxLog(Message<BasicTrx> msg, boolean response) {
      if (Objects.isNull(msg.getPayload())) {
         return "Empty payload";
      }
      BasicTrx trx = msg.getPayload();
      StringBuilder sb = new StringBuilder();
      if (response) {
         sb.append(">> OUTCOME ").append(trx.getUniqueId()).append(" >>\n");
      } else {
         sb.append("<< INCOME ").append(trx.getUniqueId()).append(" <<\n");
      }
      try {
         String content = OBJECT_MAPPER.writeValueAsString(trx);
         JsonLogger.parseJson(content, sb, 1);
      } catch (JsonProcessingException e) {
         log.error("Error parsing message for log", e);

      }

      return sb.toString();
   }

}
