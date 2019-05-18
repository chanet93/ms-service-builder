package com.glic.hostsimulator.common.log;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import wiremock.org.apache.commons.codec.CharEncoding;

public interface LogTemplateFilter<T, U> {

   Logger getLogger();

   void appendRequestLogHeader(StringBuilder body, String uniqueId, T request);

   void appendResponseLogHeader(StringBuilder body, String uniqueId, long elapsed, U response);

   Map<String, String> getRequestHeaders(T request);

   Map<String, String> getRequestQueryParams(T request);

   Map<String, String> getResponseHeaders(U response);

   boolean hasEntityPayloadRequest(T request);

   boolean hasEntityPayloadResponse(U response);

   void appendRequestPayload(StringBuilder sb, T request, int fieldId);

   void appendResponsePayload(StringBuilder sb, U response, int fieldId);

   default void logRequestData(String uniqueId, T request) {
      final StringBuilder sb = new StringBuilder();
      //Create header
      this.appendRequestLogHeader(sb, uniqueId, request);
      //Append fields
      Map<String, String> fields = new LinkedHashMap<>();
      fields.putAll(this.getRequestHeaders(request));
      fields.putAll(this.getRequestQueryParams(request));
      int fieldId = this.appendFields(sb, fields, 0);
      if (this.hasEntityPayloadRequest(request)) {
         this.appendRequestPayload(sb, request, fieldId);
      }
      getLogger().info(sb.toString());
   }

   default void logResponseData(String uniqueId, long elapsed, U response) {
      final StringBuilder sb = new StringBuilder();
      this.appendResponseLogHeader(sb, uniqueId, elapsed, response);
      //Append fields
      int fieldId = this.appendFields(sb, this.getResponseHeaders(response), 0);
      //Log response payload if present
      if (this.hasEntityPayloadResponse(response)) {
         this.appendResponsePayload(sb, response, fieldId);
      }
      getLogger().info(sb.toString());
   }

   default int appendFields(StringBuilder sb, Map<String, String> fields, int fieldId) {
      for (Map.Entry<String, String> entry : fields.entrySet()) {
         String fieldName = entry.getKey();
         String fieldValue = entry.getValue();
         addField(sb, fieldName, fieldValue, fieldId++);
      }
      return fieldId;
   }

   /**
    * This method extract the query params from the query part
    * of an url as a Map<String, List<String>>.
    *
    * @param query query
    * @return Map<String, List < String>>
    */
   default Map<String, List<String>> getQueryParams(String query) {
      Map<String, List<String>> params = new HashMap<>();
      if (query != null) {
         try {
            for (String param : query.split("&")) {
               String[] pair = param.split("=");
               if (pair.length == 0) {
                  continue;
               }
               String key = URLDecoder.decode(pair[0], CharEncoding.UTF_8);
               String value = "";
               if (pair.length > 1) {
                  value = URLDecoder.decode(pair[1], CharEncoding.UTF_8);
               }

               List<String> values = params.get(key);
               if (values == null) {
                  values = new ArrayList<>();
                  params.put(key, values);
               }
               values.add(value);
            }
         } catch (Exception e) {
            getLogger().warn("Unexpected error getting query params.", e);
         }
      }
      return params;
   }

   default void addField(StringBuilder body, String fieldName, Object value, int fieldId) {
      body.append("  [").append(fieldId).append("] ");
      body.append(fieldName).append('=');
      body.append("'").append(value).append("'\n");
   }

}
