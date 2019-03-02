/*
 * Copyright (c) 2013. VeriFone Uruguay S.A. All Rights Reserved.
 *
 * This Module contains Proprietary Information of
 * VeriFone Uruguay S.A. and should be treated as Confidential.
 *
 * This Module is provided "AS IS" Without any warranties implicit
 * or explicit.
 *
 * The use of this Module implies the acceptance of all the terms
 * and conditions of the "User License".
 */
package com.glic.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CardUtils {

   private static final String TRACK2_EQUAL_SENTINEL = "=";

   private static final String TRACK2_D_SENTINEL_CAP = "D";

   private static final String TRACK2_D_SENTINEL_LOW = "d";

   private static final String REG_EXP_MASKED_CARD = "^[0-9]{6}+([x|X]+[0-9]{4}$|[0]+$)";

   private CardUtils() {
      // Hide constructor
   }

   public static Pair<String, Integer> getTrackPANAndExp(String track2) {
      try {
         String sentinel = null;
         if (track2.contains(TRACK2_D_SENTINEL_CAP)) {
            sentinel = TRACK2_D_SENTINEL_CAP;
         }
         if (track2.contains(TRACK2_D_SENTINEL_LOW)) {
            sentinel = TRACK2_D_SENTINEL_LOW;
         }
         if (track2.contains(TRACK2_EQUAL_SENTINEL)) {
            sentinel = TRACK2_EQUAL_SENTINEL;
         }
         String[] trackSplit = track2.split(sentinel);
         if (trackSplit.length == 0) {
            return Pair.of("", 0);
         } else if (trackSplit.length == 1) {
            return Pair.of(trackSplit[0], 0);
         } else {
            return Pair.of(getCardNumber(trackSplit[0]), getExpDate(trackSplit[1]));
         }
      } catch (Exception ex) {
         throw new IllegalArgumentException(ex);
      }
   }

   public static boolean isMaskedCardNumber(String cardNumber) {
      if (cardNumber == null) {
         return false;
      }
      Pattern p = Pattern.compile(REG_EXP_MASKED_CARD);
      Matcher m = p.matcher(cardNumber);
      return m.find();
   }

   private static String getCardNumber(String cardNumber) {
      if (StringUtils.isNotBlank(cardNumber)) {
         if (!StringUtils.isNumeric(cardNumber)) {
            if (!isMaskedCardNumber(cardNumber)) {
               throw new IllegalArgumentException("Invalid card number");
            }
         }
      }
      return cardNumber;
   }

   private static Integer getExpDate(String trackRigth) {
      if (trackRigth.length() < 3) {
         throw new IllegalArgumentException("Invalid expiration date length");
      }
      String expValue = trackRigth.length() >= 4 ? trackRigth.substring(0, 4) : "0";
      return Integer.parseInt(expValue);
   }

   public static LocalDate getExpDate(TrackDataStandardUtil.TrackData trackDataExpDate) {
      try {
         return LocalDate.parse(trackDataExpDate.getExpirationDate(), DateTimeFormatter.ofPattern("YYMM"));
      } catch (Exception ex) {
         log.warn("Exp date not parseable:{}", trackDataExpDate);
      }
      return LocalDate.MIN;

   }
}
