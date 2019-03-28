package com.glic.hostsimulator.http.log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.glic.hostsimulator.common.log.LogTemplateFilter;

public class HttpLogFilter extends ResponseTransformer implements LogTemplateFilter<Request, Response> {

   private static final Logger logger = LogManager.getLogger();

   private static final String DEFAULT_LOGGER_TEMPLATE = "com.glic.hostsimulator.common.${server}";

   private static final String ROUTING_KEY = "SERVERLOGROUTE";

   private String loggerName;

   public HttpLogFilter(String serverName) {
      this.loggerName = DEFAULT_LOGGER_TEMPLATE.replace("${server}", serverName);
   }

   @Override
   public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
      ThreadContext.put(ROUTING_KEY, this.loggerName);
      //Generate random uuid value
      final String uuid = UUID.randomUUID().toString();

      logRequestData(uuid, request);

      logResponseData(uuid, 0, response);

      return response;
   }

   @Override
   public String getName() {
      return "logFilter";
   }

   @Override
   public Logger getLogger() {
      return logger;
   }

   @Override
   public void appendRequestLogHeader(StringBuilder body, String uniqueId, Request request) {
      //Append basic request data so then it can be logged
      body.append(">> INCOME >>\n").append("(+)[").append(uniqueId).append("]\n");
   }

   @Override
   public void appendResponseLogHeader(StringBuilder body, String uniqueId, long elapsed, Response response) {
      // Print information of server response
      body.append("<< OUTCOME << (").append(elapsed).append(" ms)\n").append("(+)[").append(uniqueId).append("]\n");
   }

   @Override
   public Map<String, String> getRequestHeaders(Request request) {
      Map<String, String> result = new LinkedHashMap<>();
      result.put("URL", request.getAbsoluteUrl());
      result.put("Method", request.getMethod().getName());
      result.put("ClientIp", request.getClientIp());
      result.putAll(getHeaders(request.getHeaders()));
      return result;
   }

   @Override
   public Map<String, String> getRequestQueryParams(Request request) {
      Map<String, String> result = new HashMap<>();
      try {
         for (Map.Entry<String, List<String>> entry : getQueryParams(new URL(request.getAbsoluteUrl()).getQuery()).entrySet()) {
            for (String value : entry.getValue()) {
               result.put(entry.getKey(), value);
            }
         }
      } catch (MalformedURLException e) {
         getLogger().error("Unexpected error getting query parameters from url!.");
      }
      return result;
   }

   @Override
   public Map<String, String> getResponseHeaders(Response response) {
      Map<String, String> result = new LinkedHashMap<>();
      result.put("Status", Integer.toString(response.getStatus()));
      result.put("StatusMessage", response.getStatusMessage());
      result.putAll(getHeaders(response.getHeaders()));
      return result;
   }

   @Override
   public boolean hasEntityPayloadRequest(Request request) {
      return request.getBody() != null && request.getBody().length > 0;
   }

   @Override
   public boolean hasEntityPayloadResponse(Response response) {
      return response.getBody() != null && response.getBody().length > 0;
   }

   @Override
   public void appendRequestPayload(StringBuilder sb, Request request, int fieldId) {
      addField(sb, "Body", request.getBodyAsString(), fieldId);
   }

   @Override
   public void appendResponsePayload(StringBuilder sb, Response response, int fieldId) {
      addField(sb, "Body", response.getBodyAsString(), fieldId);
   }

   private static Map<String, String> getHeaders(HttpHeaders headers) {
      Map<String, String> result = new LinkedHashMap<>();
      for (HttpHeader header : headers.all()) {
         for (String value : header.values()) {
            result.put(header.key(), value);
         }
      }
      return result;
   }

}
