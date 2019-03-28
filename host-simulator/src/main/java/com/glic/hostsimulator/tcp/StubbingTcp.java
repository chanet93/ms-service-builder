package com.glic.hostsimulator.tcp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.glic.hostsimulator.tcp.model.TcpDefaultResponseDefinition;
import com.glic.hostsimulator.tcp.model.TcpMsgStubbing;
import com.glic.hostsimulator.tcp.model.TcpResponseDefinition;
import com.glic.hostsimulator.tcp.util.IsoLogger;

import lombok.extern.log4j.Log4j2;
import wiremock.org.apache.commons.lang3.StringUtils;

@Log4j2
public class StubbingTcp {

   private static final String ECHO = "echo";

   public static ISOMsg getIsoMsgStubbedResponse(ISOMsg isoMsgRequest, ServerTcp serverTcp) throws ISOException {

      final String logPrefix = IsoLogger.getLogPrefix(serverTcp);
      log.info(logPrefix + "Received ISO:\n{}", IsoLogger.isoFormatterLog(isoMsgRequest));

      TcpMsgStubbing tcpMsgStubbing = getMockedResponse(isoMsgRequest, serverTcp.getStubs());

      if (Objects.isNull(tcpMsgStubbing)) {
         log.info(logPrefix + "No match in mappings");
         log.info(logPrefix + "Getting default response");
         tcpMsgStubbing = getDefaultResponse(serverTcp.getDefaultResponseDefinition());
      } else {
         log.info(logPrefix + "Mapping match!\nFields matched:\n{}", tcpMsgStubbing.getRequestDef().toString());
      }

      ISOMsg isoMsgResponse = serverTcp.getPackager().createISOMsg();
      isoMsgResponse.setPackager(serverTcp.getPackager());

      TcpResponseDefinition tcpResponseDefinition = tcpMsgStubbing.getResponseDef();

      for (Map.Entry<Integer, String> entry : tcpResponseDefinition.getIsoFields().entrySet()) {
         String responseValue = entry.getValue();
         if (StringUtils.equalsIgnoreCase(responseValue, ECHO)) {
            isoMsgResponse.set(entry.getKey(), isoMsgRequest.getString(entry.getKey()));
         } else {
            isoMsgResponse.set(entry.getKey(), entry.getValue());
         }
      }

      isoMsgResponse.setDirection(ISOMsg.INCOMING);
      String mti = StringUtils.isNotEmpty(tcpResponseDefinition.getMti())
            ? tcpResponseDefinition.getMti()
            : Integer.toString(Integer.parseInt(isoMsgRequest.getMTI()) + 10);
      isoMsgResponse.setMTI(mti);

      log.info(logPrefix + "Mapping response:\n{}", IsoLogger.isoFormatterLog(isoMsgResponse));

      return isoMsgResponse;
   }

   private static TcpMsgStubbing getDefaultResponse(TcpDefaultResponseDefinition defaultResponseDefinition) {
      TcpMsgStubbing msgStubbing = new TcpMsgStubbing();
      TcpResponseDefinition responseDefinition = new TcpResponseDefinition();
      responseDefinition.setIsoFields(defaultResponseDefinition.getIsoFields());
      msgStubbing.setResponseDef(responseDefinition);
      return msgStubbing;
   }

   private static TcpMsgStubbing getMockedResponse(ISOMsg isoRequest, Map<UUID, TcpMsgStubbing> stubsMap) throws ISOException {
      Map<Integer, Object> requestIsoFields = new HashMap<>();
      for (int i = 0; i <= isoRequest.getMaxField(); i++) {
         requestIsoFields.put(i, isoRequest.getString(i));
      }
      return matchRequest(stubsMap, isoRequest.getMTI(), requestIsoFields);
   }

   private static TcpMsgStubbing matchRequest(Map<UUID, TcpMsgStubbing> stubsMap, String requestMti, Map<Integer, Object> requestIsoFields) {
      TcpMsgStubbing stubbingJsonMap;
      TcpMsgStubbing maxStubbingJsonMap = null;
      int maxMatchFields = 0;

      List<Map.Entry<UUID, TcpMsgStubbing>> orderStubsMap = sortMapProiority(stubsMap.entrySet());

      for (Map.Entry<UUID, TcpMsgStubbing> entry : orderStubsMap) {
         stubbingJsonMap = entry.getValue();
         int sizeMatchFields = stubbingJsonMap.getRequestDef().getMatchingFields().size();
         int matchingFields = 0;
         if (requestMti.equals(stubbingJsonMap.getRequestDef().getMti())) {
            for (Map.Entry<Integer, String> entry2 : stubbingJsonMap.getRequestDef().getMatchingFields().entrySet()) {
               String isoField = (String) requestIsoFields.get(entry2.getKey());
               String isoFieldMatch = entry2.getValue();
               if (StringUtils.isNotEmpty(isoField) && isoField.equals(isoFieldMatch)) {
                  matchingFields++;
                  if (sizeMatchFields == matchingFields) {
                     if (matchingFields > maxMatchFields) {
                        maxMatchFields = matchingFields;
                        maxStubbingJsonMap = stubbingJsonMap;
                     }
                  }
                  continue;
               }
               matchingFields = 0;
            }
         }
      }
      return maxStubbingJsonMap;
   }

   private static List<Map.Entry<UUID, TcpMsgStubbing>> sortMapProiority(Set<Map.Entry<UUID, TcpMsgStubbing>> entries) {
      List<Map.Entry<UUID, TcpMsgStubbing>> list = new ArrayList<>(entries);
      Collections.sort(list, (o1, o2) -> o2.getValue().getRequestDef().getPriority() - o1.getValue().getRequestDef().getPriority());
      return list;
   }

}
