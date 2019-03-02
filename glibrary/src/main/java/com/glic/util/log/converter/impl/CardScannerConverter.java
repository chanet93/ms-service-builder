package com.glic.util.log.converter.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glic.util.log.converter.LogConverter;

/**
 * Created by Jorge_L2 on 14/05/2017.
 * Card scanner Converter.
 */
public class CardScannerConverter implements LogConverter {

   private static final Pattern PATTERN;

   static {
      String pattern = new StringBuilder("(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)") //
                                                                                .append("|(?<mastercard>5[1-5][0-9]{14})") //
                                                                                .append("|(?<discover>6(?:011|5[0-9]{2})[0-9]{12})") //
                                                                                .append("|(?<amex>3[47][0-9]{13})") //
                                                                                .append("|(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})") //
                                                                                .append("|(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))").toString();
      PATTERN = Pattern.compile(pattern, Pattern.DOTALL);
   }

   //TODO TERMINAR
   @Override
   public String convert(String value) {
      if (Objects.isNull(value)) {
         return null;
      }
      Matcher matcher = PATTERN.matcher(value);
      StringBuffer sb = new StringBuffer(value.length());
      while (matcher.find()) {
        // if (LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(matcher.group())) {
         //   matcher.appendReplacement(sb, Converter.CARD.convert(matcher.group()));
       //  } else {
            matcher.appendReplacement(sb, matcher.group());
        // }
      }
      matcher.appendTail(sb);
      return sb.toString();
   }

}
