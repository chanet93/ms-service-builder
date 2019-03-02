package com.glic.adminserver.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glic.adminserver.security.AppUserRepository;
import com.glic.adminserver.security.JwtTokenProvider;
import com.glic.jwt.JwtAuthenticationResponse;
import com.glic.jwt.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

   @Autowired
   AuthenticationManager authenticationManager;

   @Autowired
   AppUserRepository appUserRepository;

   @Autowired
   PasswordEncoder passwordEncoder;

   @Autowired
   JwtTokenProvider tokenProvider;

   @PostMapping("/login")
   public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

      Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      String jwt = tokenProvider.generateToken(authentication);
      JwtAuthenticationResponse response = new JwtAuthenticationResponse();
      response.setAccessToken(jwt);
      return ResponseEntity.ok(response);
   }

}
