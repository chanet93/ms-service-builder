/*
 * Copyright (c) 2013. VeriFone Uruguay S.A. All Rights Reserved.
 * 
 * This Module contains Propietary Information of
 * VeriFone Uruguay S.A. and should be treated as Confidential.
 * 
 * This Module is provided "AS IS" Without any warranties implicit
 * or explicit.
 * 
 * The use of this Module implies the acceptance of all the terms
 * and conditions of the "User License".
 */
package com.glic.util;

import static java.lang.Character.digit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * @since 15/8/2014
 */
public class HexUtil {

   private static final String[] HEX_STRING;

   static {
      HEX_STRING = new String[256];
      for (int i = 0; i < 256; i++) {
         StringBuilder d = new StringBuilder(2);
         char ch = Character.forDigit(((byte) i >> 4) & 0x0F, 16);
         d.append(Character.toUpperCase(ch));
         ch = Character.forDigit((byte) i & 0x0F, 16);
         d.append(Character.toUpperCase(ch));
         HEX_STRING[i] = d.toString();
      }
   }

   private HexUtil() {
      // Hide constructor
   }

   public static byte[] binaryLength(int length, int byteLength) {
      String sLength = StringUtils.leftPad(Integer.toHexString(length), byteLength * 2, '0');
      return hexStringToByteArray(sLength);
   }

   public static byte[] asciiLength(int length, int byteLength) {
      String sLength = StringUtils.leftPad(String.valueOf(length), byteLength, '0');
      return sLength.getBytes(StandardCharsets.US_ASCII);
   }

   public static int asciiLengthDecode(byte[] lengthHeader) {
      String length = new String(lengthHeader, StandardCharsets.US_ASCII);
      return Integer.parseInt(length);
   }

   public static byte[] hexStringToByteArray(String s) {
      int len = s.length();
      byte[] data = new byte[len / 2];

      for (int i = 0; i < len; i += 2) {
         data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
      }
      return data;
   }

   public static int getMessageLengthBCD(byte[] b) {
      return ((b[0] & 0xFF) << 24) | ((b[1] & 0xFF) << 16) | ((b[2] & 0xFF) << 8) | (b[3] & 0xFF);
   }

   public static byte[] setMessageLengthBCD(int len) {
      byte[] joinMessage = new byte[4];
      joinMessage[0] = (byte) (len >> 24);
      joinMessage[1] = (byte) (len >> 16);
      joinMessage[2] = (byte) (len >> 8);
      joinMessage[3] = (byte) (len);
      return joinMessage;
   }

   public static byte[] getFieldWithVLI(String tagValue) throws IOException {
      String len = Integer.toString(tagValue.length() / 2);
      int lenB = Integer.parseInt(len);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      baos.write(lenB);
      baos.write(hexStringToByteArray(tagValue));
      return baos.toByteArray();
   }

   public static String hexString(byte[] b) {
      StringBuilder d = new StringBuilder(b.length * 2);
      for (byte aB : b) {
         d.append(HEX_STRING[aB & 0xFF]);
      }
      return d.toString();
   }

   /**
    * Converts a hex string to a byte array.
    *
    * @param s the string to convert
    * @return the byte array
    */
   public static byte[] hex2ByteArray(String s) {
      final int len = s.length();
      final byte[] data = new byte[len / 2];
      for (int i = 0; i < len; i += 2) {
         data[i / 2] = (byte) ((digit(s.charAt(i), 16) << 4) + digit(s.charAt(i + 1), 16));
      }
      return data;
   }

   /**
    * Converts an integer to a hexadecimal string.<br>
    * Pads with one zero if the value is less or equal than 0xF.
    *
    * @param n the value
    * @return the converted value
    */
   public static String int2HexString(int n) {
      if (n <= 0xF) {
         return "0" + Integer.toHexString(n).toUpperCase(Locale.ROOT);
      }
      return Integer.toHexString(n).toUpperCase(Locale.ROOT);
   }

   public static byte[] decHexLength(int length, int byteLength) {
      String val = Integer.toHexString(length);
      return HexUtil.hex2ByteArray(StringUtils.leftPad(val, byteLength, "0"));
   }

   public static int decHexLengthDecode(byte[] lengthHeader) {
      StringBuilder sb = new StringBuilder();
      for (byte b : lengthHeader) {
         sb.append(String.format("%02x", b & 0xff));
      }
      return Integer.parseInt(sb.toString().trim(), 16);
   }

   /**
    * Generates a random hex number of a desired length.
    *
    * @param length Desired length for the resulting random hex
    * @return String composed of random hex digits
    */
   public static String generateRandomHex(int length) {
      return new SecureRandom().ints(length, 0, 16) //
            .collect(StringBuilder::new, (builder, n) -> builder.append(Integer.toHexString(n)), StringBuilder::append).toString();
   }

}
