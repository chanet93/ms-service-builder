package com.glic.hostsimulator.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.glic.hostsimulator.common.IMockServer;
import com.glic.hostsimulator.common.config.MockServer;
import com.glic.hostsimulator.common.config.MockServerConfig;
import com.glic.hostsimulator.common.exception.MockServerException;
import com.glic.hostsimulator.common.xml.util.XmlMarshallUtils;
import com.glic.hostsimulator.http.StandaloneHttpMockServer;
import com.glic.hostsimulator.tcp.StandaloneTcpMockServer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HostSimulatorStartUp implements ApplicationListener<ApplicationReadyEvent> {

   private static final String CONFIG_PATH = "classpath:config/MockServerConfig.xml";

   private boolean started = false;

   @Override
   public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
      if (!started) {
         this.started = true;
         try {
            startServers();
         } catch (Exception e) {
            log.error("Error starting", e);
         }
      }

   }

   private static void startServers() throws JAXBException, FileNotFoundException {
      List<MockServer> serverList = readConfigurations();
      IMockServer server;

      for (MockServer mockServer : serverList) {
         switch (mockServer.getServerType()) {
            case HTTP:
               server = new StandaloneHttpMockServer();
               try {
                  log.info(mockServer.toString());
                  server.startServer(mockServer);
               } catch (MockServerException e) {
                  log.warn("Exception starting server. {}.", mockServer, e);
               }
               break;
            case TCP:
               server = new StandaloneTcpMockServer();
               try {
                  log.info(mockServer.toString());
                  server.startServer(mockServer);
               } catch (MockServerException ex) {
                  log.warn("Exception starting server. {}.", mockServer, ex);
               }
               break;
         }
      }
   }

   private static List<MockServer> readConfigurations() throws JAXBException, FileNotFoundException {

      File configFile = ResourceUtils.getFile(CONFIG_PATH);
      MockServerConfig serverConfig = XmlMarshallUtils.unMarshallStringXmlObject(MockServerConfig.class, configFile);
      return serverConfig.getMockServers();
   }

}
