package com.glic.gtoken.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class TokenUtils {

   public static final String UTC = "UTC";

   public static final String SEQUENCE_PAN_SEPARATOR = ".";

   public static final String SEQUENCE_SPLIT_PAN_SEPARATOR = "\\.";

   public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";

   public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

   public static final String YYYY_MM_DD = "yyyy-MM-dd";

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

   public static Pair<String, String> getSequenceAndPan(String value) {
      if (StringUtils.isEmpty(value)) {
         return null;
      }
      String[] split = value.split(SEQUENCE_SPLIT_PAN_SEPARATOR);
      String pan = "";
      String sequence = "";
      if (split.length == 1) {
         if (value.endsWith(SEQUENCE_PAN_SEPARATOR)) {
            sequence = split[0];
         } else {
            pan = split[0];
         }
      } else if (split.length == 2) {
         sequence = split[0];
         pan = split[1];
      }
      return Pair.of(sequence, pan);
   }

   public static String constructPan(Pair<String, String> pair) {
      if (Objects.isNull(pair) || (StringUtils.isEmpty(pair.getKey()) && StringUtils.isEmpty(pair.getValue()))) {
         return "";
      } else if (StringUtils.isEmpty(pair.getKey())) {
         return Objects.toString(pair.getValue(), "");
      } else {
         return Objects.toString(pair.getKey(), "") + SEQUENCE_PAN_SEPARATOR + Objects.toString(pair.getValue(), "");
      }
   }
}
