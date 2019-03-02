package com.glic.adminserver.security;

import static com.glic.jwt.JwtUtil.ENTITY_ID_CLAIM;
import static com.glic.jwt.JwtUtil.GENERAL_ROLE_CLAIM;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

   private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

   @Value("${spring.application.jwtSecret}")
   private String jwtSecret;

   @Value("${spring.application.jwtExpirationInMs}")
   private int jwtExpirationInMs;

   public String generateToken(Authentication authentication) {

      AppUser userPrincipal = (AppUser) authentication.getPrincipal();
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

      return Jwts
            .builder()
            .setSubject(userPrincipal.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .claim(ENTITY_ID_CLAIM, userPrincipal.getEntityId())
            .claim(GENERAL_ROLE_CLAIM, userPrincipal.getRole().name())
            .compact();
   }

   public String getUserIdFromJWT(String token) {
      Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
      return claims.getSubject();
   }

   public boolean validateToken(String authToken) {
      try {
         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
         return true;
      } catch (Exception ex) {
         LOG.error("Invalid JWT signature", ex);
      }
      return false;
   }
}