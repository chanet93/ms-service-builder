package com.glic.util.log.converter;

import java.util.Map;

/**
 * Created by Jorge_L2 on 14/05/2017.
 * Log Converter interface.
 */
@FunctionalInterface
public interface LogConverter {

   default Map<String, String> parse(String value) {
      return null;
   }

   default String convert(Object value) {
      return convert(String.valueOf(value));
   }

   String convert(String value);
}
