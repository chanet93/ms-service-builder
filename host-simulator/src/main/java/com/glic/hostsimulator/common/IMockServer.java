package com.glic.hostsimulator.common;

import java.io.FileNotFoundException;

import com.glic.hostsimulator.common.config.MockServer;
import com.glic.hostsimulator.common.exception.MockServerException;

public interface IMockServer {

   String CONFIG_PATH = "config/";

   default void startServer(MockServer mockServer) throws MockServerException, FileNotFoundException {
      mockServer.validateConfig();
      String stubFolder = CONFIG_PATH + mockServer.getServerName();
      if (!mockServer.isActive()) {
         return;
      }
      if (mockServer.isSsl()) {
         this.startSslServer(mockServer.getPort(), stubFolder, mockServer.getServerName());
         return;
      }
      this.startServer(mockServer.getPort(), stubFolder, mockServer.getServerName());
   }

   void startServer(int port, String stubFileFolder, String serverName) throws MockServerException;

   void startSslServer(int port, String stubFileFolder, String serverName) throws MockServerException;

   void shutDownServer() throws MockServerException;

}
