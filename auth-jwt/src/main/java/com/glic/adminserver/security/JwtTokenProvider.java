package com.glic.adminserver.security;

import static com.glic.jwt.JwtUtil.EMAIL_CLAIM;
import static com.glic.jwt.JwtUtil.GENERAL_ROLE_CLAIM;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.glic.adminserver.model.AppUser;

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
            .claim(EMAIL_CLAIM, userPrincipal.getEmail())
            .claim(GENERAL_ROLE_CLAIM, userPrincipal.getRole())
            .compact();
   }

   public String renewToken(String token) {
      Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
      Date expiryDate = claims.getExpiration();
      Date now = new Date();
      if (expiryDate.before(now)) {
         expiryDate = new Date(now.getTime() + jwtExpirationInMs);
         return Jwts
               .builder()
               .setSubject(claims.getSubject())
               .setIssuedAt(new Date())
               .setExpiration(expiryDate)
               .signWith(SignatureAlgorithm.HS512, jwtSecret)
               .claim(EMAIL_CLAIM, claims.get(EMAIL_CLAIM))
               .claim(GENERAL_ROLE_CLAIM, claims.get(GENERAL_ROLE_CLAIM))
               .compact();

      } else {
         return "";
      }
   }

   public String getUserIdFromJWT(String token) {
      Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
      return claims.getSubject();
   }

   public boolean validateToken(String authToken) {
      if (StringUtils.isEmpty(authToken)) {
         return false;
      }

      try {
         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
         return true;
      } catch (Exception ex) {
         LOG.error("Invalid JWT signature", ex);
      }
      return false;
   }
}