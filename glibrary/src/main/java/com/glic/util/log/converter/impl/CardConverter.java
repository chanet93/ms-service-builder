package com.glic.util.log.converter.impl;

import org.apache.commons.lang3.StringUtils;

import com.glic.util.log.converter.LogConverter;

/**
 * Created by Jorge_L2 on 14/05/2017.
 * Card Converter.
 */
public final class CardConverter implements LogConverter {

   private static final int MIN_FULL_MASK = 7;

   private static final int MIN_LAST_FOUR_MASK = 14;

   private static final int LEFT = 6;

   private static final int RIGHT = 4;

   @Override
   public String convert(String value) {
      if (StringUtils.isEmpty(value)) {
         return value;
      }

      final int cardLength = value.trim().length();
      if (cardLength <= MIN_FULL_MASK) {
         return StringUtils.repeat('*', cardLength);
      } else if (cardLength < MIN_LAST_FOUR_MASK) {
         StringBuilder mask = new StringBuilder(value.trim());
         return mask.replace(0, cardLength - RIGHT, StringUtils.rightPad("*", cardLength - RIGHT, '*')).toString();
      }
      // Mask card number
      StringBuilder mask = new StringBuilder(value.trim());
      return mask.replace(LEFT, cardLength - RIGHT, StringUtils.rightPad("*", cardLength - (RIGHT + LEFT), '*')).toString();
   }

}
