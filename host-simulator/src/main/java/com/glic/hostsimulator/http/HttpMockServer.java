package com.glic.hostsimulator.http;

import static com.github.tomakehurst.wiremock.core.Options.DYNAMIC_PORT;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.Extension;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.glic.hostsimulator.common.exception.MockServerException;

import wiremock.com.google.common.io.Resources;

public class HttpMockServer {

   private static final String KEY_STORE_FILE_NAME = "mock-server-ssl-keystore";

   private static final String KEY_STORE_PSW = "password";

   private static final String STUB_FILE_EXTENSION = ".json";

   private WireMockServer wireMockServer = null;

   protected WireMockConfiguration getHttpWireMockConfiguration(int port, Extension... extensions) throws MockServerException {
      WireMockConfiguration result = HttpMockServer.getBasicServerOptionConfig(extensions);
      result.port(port);
      return result;
   }

   protected WireMockConfiguration getHttpsWireMockConfiguration(int port, Extension... extensions) throws MockServerException {
      WireMockConfiguration result = HttpMockServer.getBasicServerOptionConfig(extensions);
      result.port(DYNAMIC_PORT);
      result.httpsPort(port);
      result.keystorePath(getKeyStorePath());
      result.keystorePassword(KEY_STORE_PSW);
      return result;
   }

   public void startServer(int port, WireMockConfiguration options) throws MockServerException {
      if (isRunning()) {
         throw new MockServerException("Server already running!.");
      }
      try {
         this.wireMockServer = new WireMockServer(options);
         this.wireMockServer.start();
         WireMock.configureFor(port);
      } catch (Exception e) {
         throw new MockServerException("Unexpected error starting http server!.", e);
      }
   }

   public final void addMapping(StubMapping mapping) throws MockServerException {
      if (!isRunning()) {
         throw new MockServerException("Unexpected error adding stub for server as it must be running in order to add stubs!.");
      }
      try {
         this.wireMockServer.addStubMapping(mapping);
      } catch (Exception e) {
         throw new MockServerException("Unexpected error adding stub for server!.", e);
      }
   }

   public final void addMappingFromJson(String json) throws MockServerException {
      try {
         this.addMapping(Json.read(json, StubMapping.class));
      } catch (MockServerException e) {
         throw e;
      } catch (Exception e) {
         throw new MockServerException("Unexpected error adding json stub for server!.", e);
      }
   }

   public final void addMappingFromFile(String jsonFilePath) throws MockServerException {
      try {
         String json = Resources.toString(Resources.getResource(jsonFilePath), StandardCharsets.UTF_8);
         this.addMappingFromJson(json);
      } catch (MockServerException e) {
         throw e;
      } catch (Exception e) {
         throw new MockServerException("Unexpected error adding json stub file for server!.", e);
      }
   }

   public final void addMappingFromFolder(String jsonFolderPath) throws MockServerException {
      try {
         List<String> files = Resources.readLines(Resources.getResource(jsonFolderPath), StandardCharsets.UTF_8);
         for (String file : files) {
            if (file.endsWith(STUB_FILE_EXTENSION)) {
               this.addMappingFromFile(jsonFolderPath + "/" + file);
            }
         }
      } catch (MockServerException e) {
         throw e;
      } catch (Exception e) {
         throw new MockServerException("Unexpected error adding json stub folder for server!.", e);
      }
   }

   public void startHttpServer(int port) throws MockServerException {
      this.startHttpServer(port, (Extension[]) null);
   }

   public void startHttpServer(int port, Extension... extensions) throws MockServerException {
      this.startServer(port, this.getHttpWireMockConfiguration(port, extensions));
   }

   public void startHttpsServer(int port) throws MockServerException {
      this.startHttpsServer(port, (Extension[]) null);
   }

   public void startHttpsServer(int port, Extension... extensions) throws MockServerException {
      this.startServer(port, this.getHttpsWireMockConfiguration(port, extensions));
   }

   public boolean isRunning() {
      return this.wireMockServer != null && this.wireMockServer.isRunning();
   }

   public final void shutDownServer() throws MockServerException {
      try {
         if (isRunning()) {
            this.wireMockServer.shutdownServer();
         }
      } catch (Exception e) {
         throw new MockServerException("Unexpected error while shutting down server!.", e);
      }
   }

   private static WireMockConfiguration getBasicServerOptionConfig(Extension... extensions) throws MockServerException {
      try {
         WireMockConfiguration options = wireMockConfig();
         options.disableRequestJournal();
         options.maxRequestJournalEntries(100);
         if (extensions != null && extensions.length > 0) {
            options.extensions(extensions);
         }
         return options;
      } catch (Exception e) {
         throw new MockServerException("Unexpected error creating server config!.", e);
      }
   }

   private static String getKeyStorePath() throws MockServerException {
      try {
         return Paths.get(ClassLoader.getSystemResource(KEY_STORE_FILE_NAME).toURI()).toString();
      } catch (Exception e) {
         throw new MockServerException("Unexpected error getting keystore!.", e);
      }
   }

}
