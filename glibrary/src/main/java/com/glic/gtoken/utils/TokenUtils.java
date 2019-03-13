package com.glic.gtoken.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

public class TokenUtils {

   public static final String UTC = "UTC";

   public static final String SEQUENCE_SPLIT_PAN_SEPARATOR = "\\.";

   public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

   public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

   public static final String YYYY_MM_DD = "yyyy-MM-dd";

   private static final String DEFAULT_DAY = "01";

   public static Date getTokenExpire(String tokenExpiryDate) throws ParseException {
      return TokenUtils.getDateFromString(TokenUtils.YYYY_MM_DD, tokenExpiryDate);
   }

   public static Date getDateTimeRFC339(String sDate) throws ParseException {
      if (StringUtils.isEmpty(sDate)) {
         return null;
      }
      SimpleDateFormat df = new SimpleDateFormat(TokenUtils.YYYY_MM_DD_T_HH_MM_SS);
      return df.parse(sDate);
   }

   public static String getDateInString(String format, Date date) {
      if (Objects.isNull(date)) {
         return null;
      }
      DateFormat df = new SimpleDateFormat(format);
      return df.format(date);
   }

   public static Date getDateFromString(String dformat, String sDate) throws ParseException {
      if (StringUtils.isEmpty(sDate)) {
         return null;
      } else {
         DateFormat format = new SimpleDateFormat(dformat);
         return format.parse(sDate);
      }
   }

}
