/*
 * Copyright (c) 2014.  VeriFone Uruguay S.A. All Rights Reserved.
 *
 * This Module contains Proprietary Information of
 * VeriFone Uruguay S.A. and should be treated as Confidential.
 *
 * This Module is provided "AS IS" Without any warranties implicit
 * or explicit.
 *
 * The use of this Module implies the acceptance of all the terms
 * and conditions of the "User License".
 */
package com.glic.payment.constants;

/**
 * @author diego_m1
 *         Visa: Field 22 POS Entry Mode Codes
 *         Mastercard: Field 22 Subfield 1 POS Terminal PAN Entry Mode
 */
public enum EPosEntryModePANEntry {
   /**
    * PAN entry mode unknown.
    */
   UNKNOWN("00"),
   /**
    * PAN manual entry.
    */
   MANUAL_ENTRY("01"),
   /**
    * PAN auto-entry via magnetic stripe—track data is not required.
    * OR
    * The acquirer is not qualified to submit magnetic stripe transactions, so MasterCard replaced value 90 or 91 with value 02.
    */
   MAGNETIC_STRIPE("02"),
   /**
    * PAN auto-entry via bar code reader.
    */
   BARCODE("03"),
   /**
    * PAN auto-entry via optical character reader (OCR).
    */
   OCR("04"),
   /**
    * PAN auto-entry via chip.
    */
   CONTACTLESS("05"),
   /**
    * PAN auto-entry via contactless M/Chip.
    */
   CHIP("07"),
   /**
    * Digital Secure Remote Payment.
    */
   EMV_DIGITAL_SECURE_REMOTE_PAYMENT("09"),
   /**
    * A hybrid terminal with an online connection to the acquirer failed in sending a
    * chip fallback transaction (in which DE 22, subfield 1 = 80) to the issuer.
    * or
    * A hybrid terminal with no online connection to the acquirer failed to read the chip
    * card. The merchant is prompted to read the magnetic stripe from the card, the magstripe
    * is successfully read and indicates a service code 2XX (or 6XX if card is domestic).
    * Mastercard only.
    */
   MANUAL_ENTRY_TERMINAL_FAIL("10"),
   MANUAL_ENTRY_NO_TERMINAL("11"),
   CONTACTLESS_CHIP("12"),
   /**
    * Chip card at chip-capable terminal was unable to process transaction using data on the chip;
    * therefore, the terminal defaulted to the magnetic stripe-read PAN.
    * Mastercard only.
    */
   FALLBACK("80"),
   /*
   * Magnetic – Track 1
   */
   MAGNETIC_STRIPE_READ_TRACK_I("81"),
   /**
    * PAN Auto Entry via Server (issuer, acquirer, or third party vendor system).
    * This value will also be sent to issuers when a MasterCard Digital Enablement Service token
    * was used to initiate the transaction and acquirer DE 22, subfield 1 = 07 or 91.
    * Mastercard only.
    */
   CONTACTLESS_MOBILE("82"),
   /**
    * Magnetic stripe read and exact content of Track 1 or Track 2 included (CVV check is possible).
    * Visa, Mastercard.
    */
   MAGNETIC_STRIPE_TRACK_READ("90"),
   /**
    * Contactless using Magnetic Stripe rules.
    * Visa, Mastercard.
    */
   CONTACTLESS_MAGNETIC_STRIPE("91"),
   /**
    * Integrated Circuit Read.
    * Visa only. Chip card with unreliable Card Verification Value (CVV) data.
    */
   ICR("95"),
   /**
    * Contactless chip card unable to send data by contactless technology;
    * therefore, the terminal defaulted to the chip-read PAN.
    */
   CONTACTLESS_CHIP_FALLBACK("96"),
   /**
    * Chip card with PIN.
    */
   CHIP_WITH_PIN("97"),
   /**
    * PAN manual entry with signature.
    */
   MANUAL_ENTRY_SIGNATURE("98"),
   /**
    * Magnetic Mobile Contactless.
    */
   CONTACTLESS_MAGNETIC_STRIPE_MOBILE("99"),
   /**
    * Empty AID Candidate List.
    */
   EMPTY_AID_FALLBACK_TO_MSR("100");

   private final String value;

   private EPosEntryModePANEntry(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

   public static EPosEntryModePANEntry getWithCode(String value) {
      for (EPosEntryModePANEntry v : values()) {
         if (v.getValue().equalsIgnoreCase(value)) {
            return v;
         }
      }
      throw new IllegalArgumentException();
   }
}
