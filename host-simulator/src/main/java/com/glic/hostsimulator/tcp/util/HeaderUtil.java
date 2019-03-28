package com.glic.hostsimulator.tcp.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.netty.buffer.ByteBuf;


public final class HeaderUtil {

   public static final String RESPONSE_HEADER_NULL_ISO = "PP|0002|002000|000016|000000|00|000001|{}|2000020060057000";

   private static final String PP = "PP";

   private static final int HEADER_SIZE = 62;

   private HeaderUtil() {
      // Hide constructor.
   }

   public static boolean isNotIso(ByteBuf msg) {
      final byte[] array = new byte[1];
      msg.getBytes(0, array, 0, 1);
      return array[0] == '#';
   }

   public static boolean checkMessageCode(ByteBuf msg, String messageCode) {
      return checkMessageCode(msg, messageCode, false);
   }

   public static boolean checkMessageCode(ByteBuf msg, String messageCode, boolean shortMsgCode) {
      if (msg.readableBytes() < HEADER_SIZE) {
         return false;
      }

      final String[] headerParts = parseHeader(msg);
      if (headerParts.length == 9) {
         final String mCode = shortMsgCode ? headerParts[8].substring(0, 4) : headerParts[8];
         return PP.equals(headerParts[0]) && messageCode.equals(mCode);
      }
      return false;
   }

   private static String[] parseHeader(ByteBuf msg) {
      final byte[] array = new byte[HEADER_SIZE];
      msg.getBytes(0, array, 0, HEADER_SIZE);
      final String header = new String(array, UTF_8);
      return header.split("\\|");
   }
}
