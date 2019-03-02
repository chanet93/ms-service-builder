package com.glic.adminserver;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServicesAuthJwtApplication {

   public static void main(String[] args) {
      new SpringApplicationBuilder(ServicesAuthJwtApplication.class).web(WebApplicationType.SERVLET).run(args);
   }
}
