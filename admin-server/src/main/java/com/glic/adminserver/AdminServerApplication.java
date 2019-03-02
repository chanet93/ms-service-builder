package com.glic.adminserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class AdminServerApplication {

   public static void main(String[] args) {
      new SpringApplicationBuilder(AdminServerApplication.class).run(args);
   }
}
