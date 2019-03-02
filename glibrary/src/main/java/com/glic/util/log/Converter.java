package com.glic.util.log;

import com.glic.util.log.converter.LogConverter;
import com.glic.util.log.converter.impl.CardConverter;
import com.glic.util.log.converter.impl.CardScannerConverter;
import com.glic.util.log.converter.impl.TrackConverter;

/**
 * Converter.
 */
public class Converter {

   public static final LogConverter DEF = new DefaultConverter();

   public static final LogConverter HIDE = new HideConverter();

   public static final LogConverter CARD = new CardConverter();

   public static final LogConverter TRACK = new TrackConverter();

   public static final LogConverter CARD_SCANNER = new CardScannerConverter();

   public static final LogConverter NEW_LINE = new NewLineConverter();

   private static final String HIDE_VALUE = "[..]";

   private Converter() {
      // Hide constructor
   }

   /**
    * Default Converter.
    */
   private static class DefaultConverter implements LogConverter {

      @Override
      public String convert(String value) {
         return value;
      }

   }

   /**
    * Hide Converter.
    */
   private static class HideConverter implements LogConverter {

      @Override
      public String convert(String value) {
         return HIDE_VALUE;
      }

   }

   /**
    * New line Converter.
    */
   private static class NewLineConverter implements LogConverter {

      @Override
      public String convert(String value) {
         return String.format("%n%s", value);
      }

   }

}
