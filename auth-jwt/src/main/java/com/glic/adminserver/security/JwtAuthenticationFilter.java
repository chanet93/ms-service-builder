package com.glic.adminserver.security;

import static com.glic.jwt.JwtUtil.getJwtFromRequest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.glic.jwt.JwtUtil;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

   @Autowired
   private JwtTokenProvider tokenProvider;

   @Autowired
   private CustomUserDetailsService customUserDetailsService;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {
      try {
         String jwt = getJwtFromRequest(request);
         if (StringUtils.hasText(jwt)) {
            if (tokenProvider.validateToken(jwt)) {
               String email = tokenProvider.getUserIdFromJWT(jwt);
               UserDetails userDetails = customUserDetailsService.loadUserById(email);
               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                     userDetails.getAuthorities());
               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
               filterChain.doFilter(request, response);
            } else {
               //set remove cookie just in case
               Cookie cookie = JwtUtil.getJwtCookie(jwt);
               cookie.setMaxAge(0);
               response.addCookie(cookie);
               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            }
         } else {
            filterChain.doFilter(request, response);
         }
      } catch (Exception ex) {
         LOG.error("Could not set user authentication in security context", ex);
         filterChain.doFilter(request, response);
      }

   }

}

