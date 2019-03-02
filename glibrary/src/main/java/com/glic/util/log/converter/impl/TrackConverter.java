package com.glic.util.log.converter.impl;

import org.apache.commons.lang3.StringUtils;

import com.glic.util.log.converter.LogConverter;

/**
 * Created by Jorge_L2 on 14/05/2017.
 */
public final class TrackConverter implements LogConverter {

   @Override
   public String convert(String value) {
      final int cardLength = value.trim().length();
      if (StringUtils.isEmpty(value) || cardLength <= 10) {
         return StringUtils.repeat('*', cardLength);
      }
      // Mask card number
      StringBuilder mask = new StringBuilder(value.trim());
      return mask.replace(6, cardLength, StringUtils.rightPad("*", cardLength - 6, '*')).toString();
   }

}
