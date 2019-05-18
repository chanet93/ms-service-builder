package com.glic.adminserver;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.glic.jwt.JwtAuthenticationResponse;
import com.glic.jwt.LoginRequest;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class ServicesAuthJwtApplicationTests {

   @LocalServerPort
   private int port;

   @Autowired
   private TestRestTemplate restT;

   @Test
   public void happyPass() {
      LoginRequest request = new LoginRequest();
      request.setEmail("root@root.com");
      request.setPassword("root");
      ResponseEntity<JwtAuthenticationResponse> serviceResponse = this.restT.postForEntity("http://localhost:" + port + "/auth/login", request,
            JwtAuthenticationResponse.class);
      Assert.assertTrue(serviceResponse.getStatusCode().is2xxSuccessful());
      log.info("Response:{}", serviceResponse.getBody().getAccessToken());

   }

}
