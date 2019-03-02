package com.glic.jwt;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class JwtUtil {

   public static final String ENTITY_ID_CLAIM = "ENTITY_ID";
   public static final String GENERAL_ROLE_CLAIM = "GENERAL_ROLE_CLAIM";

   public static String getJwtFromRequest(HttpServletRequest request) {
      String bearerToken = request.getHeader("Authorization");
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7, bearerToken.length());
      }
      return null;
   }




}
