package com.glic.util;

import java.time.LocalDateTime;

public class DateTimeUtils {

   public static LocalDateTime getPreviousValue() {
      return LocalDateTime.now().minusYears(1);
   }

}
