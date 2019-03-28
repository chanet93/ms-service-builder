package com.glic.hostsimulator.http;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.glic.hostsimulator.common.IMockServer;
import com.glic.hostsimulator.common.exception.MockServerException;
import com.glic.hostsimulator.http.log.HttpLogFilter;


public class StandaloneHttpMockServer extends HttpMockServer implements IMockServer {

   @Override
   public void startServer(int port, String stubFileFolder, String serverName) throws MockServerException {
      WireMockConfiguration config = super.getHttpWireMockConfiguration(port, new HttpLogFilter(serverName));
      config.usingFilesUnderClasspath(stubFileFolder);
      super.startServer(port, config);
   }

   @Override
   public void startSslServer(int port, String stubFileFolder, String serverName) throws MockServerException {
      WireMockConfiguration config = super.getHttpsWireMockConfiguration(port, new HttpLogFilter(serverName));
      config.usingFilesUnderClasspath(stubFileFolder);
      super.startServer(port, config);
   }

}
