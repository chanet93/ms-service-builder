package com.glic.adminserver.security;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SecureRandomService {

   public static final int HASH_ITERATION = 10;

   private static final SecureRandom random = new SecureRandom();

   public String getActivationToken() {
      return StringUtils.upperCase(new BigInteger(280, random).toString(32));
   }

}
