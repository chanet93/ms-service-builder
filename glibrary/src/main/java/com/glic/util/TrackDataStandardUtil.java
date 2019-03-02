package com.glic.util;

import org.apache.commons.lang3.StringUtils;


public class TrackDataStandardUtil {

   private static final String END_TRACK = "?";

   private static final String END_TRACK_HEX = "F";

   private static final String BEGIN_TRACK1A = "%B";

   private static final String BEGIN_TRACK1B = "B";

   private static final String DELIMIT_TRACK1 = "^";

   private static final String BEGIN_TRACK2 = ";";

   private static final String BEGIN_TRACK2_HEX = "B";

   private static final String DELIMIT_TRACK2 = "=";

   private static final String DELIMIT_TRACK2_HEX = "D";

   private static final String SENSITIVE_MASK_CHAR = "9";

   private static final Integer TRACK2_SENSITIVE_DATA_DEF_LENGTH = 8;

   private static final Integer TRACK2_EXPIRY_LENGTH = 4;

   private static final Integer TRACK2_SERVICE_CODE_LENGTH = 3;

   private TrackDataStandardUtil() {
      // Hide constructor
   }

   public static TrackData getTrackData(String decryptedPanAndExpDate) {
      String expDate = decryptedPanAndExpDate.substring(decryptedPanAndExpDate.length() - 4, decryptedPanAndExpDate.length());
      String cardNumber = decryptedPanAndExpDate.substring(0, decryptedPanAndExpDate.length() - 4);
      return TrackData.newBuilder().withHasTrack1(true).withTrack1Card(cardNumber).withTrack1Expiry(expDate).build();
   }

   public static TrackData parseTrackData(String argTracks) {
      if (argTracks == null) {
         return null;
      }
      return TrackData.newBuilder().build().parseTracks(argTracks);
   }

   /**
    * Mask the track2 sensitive data.
    *
    * @param track2 the track 2
    * @return the masked track 2
    */
   public static String maskTrack2SensitiveData(String track2) {
      if (StringUtils.isEmpty(track2)) {
         throw new IllegalArgumentException("Track2 data cannot be empty");
      }

      final StringBuilder sb = new StringBuilder(track2);

      final int track2Length = track2.length();
      final boolean sentinel = track2.endsWith(END_TRACK);
      // the masked data begins after the expiration date and service code
      final int begin = track2.lastIndexOf(DELIMIT_TRACK2) + 1 + TRACK2_EXPIRY_LENGTH + TRACK2_SERVICE_CODE_LENGTH;

      final int maskedDataLength;
      if (begin + TRACK2_SENSITIVE_DATA_DEF_LENGTH + 1 > track2Length) {
         // in case the track data has less than
         // TRACK2_SENSITIVE_DATA_DEF_LENGTH characters after the begin
         maskedDataLength = track2Length - begin - (sentinel ? 1 : 0);
      } else {
         maskedDataLength = TRACK2_SENSITIVE_DATA_DEF_LENGTH;
      }
      final int end = begin + maskedDataLength;
      final String maskedData = StringUtils.repeat(SENSITIVE_MASK_CHAR, maskedDataLength);

      sb.replace(begin, end, maskedData);
      return sb.toString();
   }

   /**
    * @author: William Maddox
    * @since: ShopNow.com - eBusiness 1999
    * <p>
    * PURPOSE
    * <p>
    * Parses Track 1 and/or Track2 data from magnetic strip reader.
    * <p>
    * USAGE
    * <p>
    * The argument to parseTracks() should be Track1 and/or Track2 read
    * from a magnetic strip card reader. Framing characters are only
    * required if multiple tracks are supplied; or, if trailing or
    * leading non-track data is included.
    * <p>
    * In the case that Track 1 data is included, if it is of standard
    * format, as opposed to a proprietary format, it will be parsed and
    * getHasStandardTrack1() will return 'true'.
    */
   public static class TrackData {

      String tracks = "";

      boolean hasTrack1 = false;

      boolean hasStandardTrack1 = false;

      boolean hasTrack2 = false;

      String track1 = "";

      // 080124P030837 >>
      String track1ANSI = "";

      // 080124P030837 <<
      String track1Card = "";

      String track1Cardholder = "";

      String track1Expiry = "";

      String track2 = "";

      String track2Card = "";

      String track2Expiry = "";

      // 070430P125152 >>
      String track1ServiceCode = "";

      String track2ServiceCode = "";

      public TrackData() {
      }

      private TrackData(Builder builder) {
         tracks = builder.tracks;
         hasTrack1 = builder.hasTrack1;
         hasStandardTrack1 = builder.hasStandardTrack1;
         hasTrack2 = builder.hasTrack2;
         track1 = builder.track1;
         track1ANSI = builder.track1ANSI;
         track1Card = builder.track1Card;
         track1Cardholder = builder.track1Cardholder;
         track1Expiry = builder.track1Expiry;
         track2 = builder.track2;
         track2Card = builder.track2Card;
         track2Expiry = builder.track2Expiry;
         track1ServiceCode = builder.track1ServiceCode;
         track2ServiceCode = builder.track2ServiceCode;
      }

      public String getExpirationDate() {
         if (!StringUtils.isEmpty(getTrack1Expiry())) {
            return invertCardExpDate(getTrack1Expiry());
         } else if (!StringUtils.isEmpty(getTrack2Expiry())) {
            return getTrack2Expiry();
         }
         throw new IllegalArgumentException("No expiration date information found.");
      }

      public Integer getExpirationDateFollowOn() {
         if (!StringUtils.isEmpty(getTrack1Expiry())) {
            return Integer.valueOf(getTrack1Expiry());
         } else if (!StringUtils.isEmpty(getTrack2Expiry())) {
            return Integer.valueOf(getTrack2Expiry());
         }
         throw new IllegalArgumentException("No expiration date information found.");
      }

      public String getCardNumber() {
         if (!StringUtils.isEmpty(getTrack1Card())) {
            return getTrack1Card();
         } else if (!StringUtils.isEmpty(getTrack2Card())) {
            return getTrack2Card();
         }
         throw new IllegalArgumentException("No track information found.");
      }

      public String getCardHolder() {
         if (!StringUtils.isEmpty(getTrack1Cardholder())) {
            return getTrack1Cardholder();
         }
         return null;
      }

      public boolean getHasStandardTrack1() {
         return hasStandardTrack1;
      }

      // 080124P030837 >>
      public String getTrack1ANSI() {
         return track1ANSI;
      }

      // 070430P125152 <<

      // 080124P030837 <<
      public String getTrack1() {
         return track1;
      }

      public String getTrack1Card() {
         return track1Card;
      }

      public String getTrack1Cardholder() {
         return track1Cardholder;
      }

      public String getTrack1Expiry() {
         return track1Expiry;
      }

      // 070430P125152 >>
      public String getTrack1ServiceCode() {
         return track1ServiceCode;
      }

      // 070430P125152 <<
      // nathan_n1 02/09/2010 080825P125710
      public boolean getHasTrack1() {
         return hasTrack1;
      }

      public boolean getHasTrack2() {
         return hasTrack2;
      }

      public String getTrack2() {
         return track2;
      }

      public String getTrack2Card() {
         return track2Card;
      }

      public String getTrack2Expiry() {
         return track2Expiry;
      }

      // 070430P125152 >>
      public String getTrack2ServiceCode() {
         return track2ServiceCode;
      }

      // 070430P125152 <<
      public TrackData parseTracks(String argTracks) {

         int i = 0;
         int begin = 0;
         int end = 0;

         int delimit1Track1 = -1;
         int delimitTrack2 = -1;

         hasTrack1 = false;
         hasTrack2 = false;
         track1 = "";
         track2 = "";

         tracks = argTracks;

         if ((delimit1Track1 = tracks.indexOf(DELIMIT_TRACK1)) > 0) {
            hasTrack1 = true;
         }

         if ((delimitTrack2 = tracks.indexOf(DELIMIT_TRACK2)) > 0) {
            hasTrack2 = true;
         }

         if (!(hasTrack1 || hasTrack2) && (delimitTrack2 = tracks.indexOf(DELIMIT_TRACK2_HEX)) > 0) {
            tracks = convertHexToASCII(tracks);
            hasTrack2 = true;
         }

         if (!(hasTrack1 || hasTrack2)) {
            throw new IllegalArgumentException("Invalid track data.");
         }

         if (hasTrack1) {
            begin = tracks.lastIndexOf(BEGIN_TRACK1A, delimit1Track1);
            if (begin < 0) {
               if (tracks.startsWith(BEGIN_TRACK1B)) {
                  hasStandardTrack1 = true;
                  begin = 0;
               }
            } else {
               hasStandardTrack1 = true;
               begin += 1;
            }

            if (hasStandardTrack1) {
               end = tracks.indexOf(END_TRACK);
               if (end < 0) {
                  end = tracks.length();
               }

               track1 = tracks.substring(begin, end);

               // must remove spaces in card number field before
               // sending to VISA
               StringBuilder temp = new StringBuilder();
               char chr;
               end = track1.indexOf(DELIMIT_TRACK1);
               // 080124P030837 >>
               track1ANSI = track1;
               // 080124P030837 <<
               for (i = 0; i < end; i++) {
                  if ((chr = track1.charAt(i)) != ' ') {
                     temp.append(chr);
                  }
               }
               temp.append(track1.substring(end, track1.length()));
               track1 = new String(temp);
               //

               // C-3 03/16/07 Validate length
               if (track1.length() > 76) {
                  throw new IllegalArgumentException("Track 1 Length Greater Than 76 Characters.");
               }
               // end C-3

               track1Card = track1.substring(1, track1.indexOf(DELIMIT_TRACK1));

               begin = track1.indexOf(DELIMIT_TRACK1) + 1;
               end = track1.lastIndexOf(DELIMIT_TRACK1);
               track1Cardholder = track1.substring(begin, end).trim();

               begin = end + 1;
               track1Expiry = track1.substring(begin, begin + 4);
               // 070430P125152 >>
               begin = begin + 4;
               // CAC 10/31/2007 Track Record DCR 000722
               if (begin + 3 <= track1.length()) {
                  track1ServiceCode = track1.substring(begin, begin + 3);
               }
               // 070430P125152 <<
            }
         }

         if (hasTrack2) {
            if (hasTrack1) {
               begin = tracks.lastIndexOf(BEGIN_TRACK2, delimitTrack2) + 1;
            } else {
               begin = 0;
               while (!Character.isDigit(tracks.charAt(begin))) {
                  begin++;
               }
            }

            end = tracks.indexOf(END_TRACK, delimitTrack2);
            if (end < 0) {
               end = tracks.length();
            }

            track2 = tracks.substring(begin, end);

            // C-3 03/16/07 Validate length
            if (track2.length() > 37) {
               throw new IllegalArgumentException("Track 2 Length Greater Than 37 Characters.");
            }
            // end C-3

            track2Card = track2.substring(0, track2.indexOf(DELIMIT_TRACK2));

            begin = track2.lastIndexOf(DELIMIT_TRACK2) + 1;
            track2Expiry = track2.substring(begin, begin + 4);
            // 070430P125152 >>
            begin = begin + 4;
            // C-3 08/20/07 DCR050920A115321 Fleet
            if (begin + 3 <= track2.length()) {
               track2ServiceCode = track2.substring(begin, begin + 3);
            }
            // 070430P125152 <<
         }

         return this;
      }

      private static String convertHexToASCII(String track) {
         return track.replace(BEGIN_TRACK2_HEX, BEGIN_TRACK2).replace(DELIMIT_TRACK2_HEX, DELIMIT_TRACK2).replace(END_TRACK_HEX, END_TRACK);
      }

      public static Builder newBuilder() {
         return new Builder();
      }

      public static String invertCardExpDate(String manualCardExpDate) {
         String datexp = String.valueOf(manualCardExpDate);
         if (datexp.length() == 4) {
            return datexp.substring(2, 4) + datexp.substring(0, 2);
         }
         return datexp;
      }

      /**
       * Builder.
       */
      public static final class Builder {

         private String tracks;

         private boolean hasTrack1;

         private boolean hasStandardTrack1;

         private boolean hasTrack2;

         private String track1;

         private String track1ANSI;

         private String track1Card;

         private String track1Cardholder;

         private String track1Expiry;

         private String track2;

         private String track2Card;

         private String track2Expiry;

         private String track1ServiceCode;

         private String track2ServiceCode;

         private Builder() {
         }

         public Builder withTracks(String tracks) {
            this.tracks = tracks;
            return this;
         }

         public Builder withHasTrack1(boolean hasTrack1) {
            this.hasTrack1 = hasTrack1;
            return this;
         }

         public Builder withHasStandardTrack1(boolean hasStandardTrack1) {
            this.hasStandardTrack1 = hasStandardTrack1;
            return this;
         }

         public Builder withHasTrack2(boolean hasTrack2) {
            this.hasTrack2 = hasTrack2;
            return this;
         }

         public Builder withTrack1(String track1) {
            this.track1 = track1;
            return this;
         }

         public Builder withTrack1ANSI(String track1ANSI) {
            this.track1ANSI = track1ANSI;
            return this;
         }

         public Builder withTrack1Card(String track1Card) {
            this.track1Card = track1Card;
            return this;
         }

         public Builder withTrack1Cardholder(String track1Cardholder) {
            this.track1Cardholder = track1Cardholder;
            return this;
         }

         public Builder withTrack1Expiry(String track1Expiry) {
            this.track1Expiry = track1Expiry;
            return this;
         }

         public Builder withTrack2(String track2) {
            this.track2 = track2;
            return this;
         }

         public Builder withTrack2Card(String track2Card) {
            this.track2Card = track2Card;
            return this;
         }

         public Builder withTrack2Expiry(String track2Expiry) {
            this.track2Expiry = track2Expiry;
            return this;
         }

         public Builder withTrack1ServiceCode(String track1ServiceCode) {
            this.track1ServiceCode = track1ServiceCode;
            return this;
         }

         public Builder withTrack2ServiceCode(String track2ServiceCode) {
            this.track2ServiceCode = track2ServiceCode;
            return this;
         }

         public TrackData build() {
            return new TrackData(this);
         }
      }
   }
}
