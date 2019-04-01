package com.glic.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

public class JwtUtil {

   public static final String EMAIL_CLAIM = "EMAIL_CLAIM";

   public static final String GENERAL_ROLE_CLAIM = "GENERAL_ROLE_CLAIM";

   public static final String JWT_COOKIE_NAME = "JWT";

   public static String getJwtFromRequest(HttpServletRequest request) {

      //Find token on header
      String bearerToken = request.getHeader("Authorization");
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }

      //find token on cookie
      Cookie cookie = WebUtils.getCookie(request, JWT_COOKIE_NAME);
      if (cookie != null && !StringUtils.isEmpty(cookie.getValue())) {
         return cookie.getValue();
      }

      return null;
   }

   public static Cookie getJwtCookie(String JwtToken) {
      Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, JwtToken);
      //TODO add SSL and change this to secure.
      jwtCookie.setSecure(false);
      jwtCookie.setHttpOnly(false);
      jwtCookie.setMaxAge(-1);
      jwtCookie.setPath("/");
      return jwtCookie;
   }

}
