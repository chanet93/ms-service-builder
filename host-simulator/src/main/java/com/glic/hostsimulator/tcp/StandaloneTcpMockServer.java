package com.glic.hostsimulator.tcp;

import java.io.FileNotFoundException;

import com.glic.hostsimulator.common.IMockServer;
import com.glic.hostsimulator.common.config.MockServer;
import com.glic.hostsimulator.common.exception.MockServerException;

public class StandaloneTcpMockServer extends TcpMockServer implements IMockServer {

   @Override
   public void startServer(MockServer mockServer) throws MockServerException, FileNotFoundException {
      super.startServer(mockServer);
   }

   @Override
   public void startServer(int port, String stubFileFolder, String serverName) throws MockServerException {

   }

   @Override
   public void startSslServer(int port, String stubFileFolder, String serverName) throws MockServerException {

   }

   @Override
   public void shutDownServer() throws MockServerException {

   }

}
