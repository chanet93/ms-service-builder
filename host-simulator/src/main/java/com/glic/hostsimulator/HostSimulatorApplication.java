package com.glic.hostsimulator;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class HostSimulatorApplication {

   public static void main(String[] args) {
      new SpringApplicationBuilder(HostSimulatorApplication.class).web(WebApplicationType.SERVLET).run(args);
   }
}
