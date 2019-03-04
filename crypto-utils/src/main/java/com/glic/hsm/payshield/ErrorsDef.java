package com.glic.hsm.payshield;

/**
 * Definition of the error codes for the thales crypto 9000
 *
 * @author ErnestoQ1
 * @date 24/10/2014
 */
public class ErrorsDef {

   public static final String NO_ERROR = "00";

   public static final String HMAC_VERIFICATION_FAILURE = "01";

   public static final String NOT_SINGLE_LENGHT = "02"; // Warning PVK not single length

   public static final String INVALID_NUMBERS_COMPONENTS = "03";

   public static final String INVALID_HASH_IDENTIFIER = "05"; // Invalid hash identifier

   public static final String COMPONENT_PARITY_ERROR = "10"; // ZMK or TMK Parity error / Current TMK, TPK or PVK parity error

   public static final String KEY_PARITY_ERROR = "11"; // Key parity error, advice only / Stored TMK, TPK or PVK parity error

   public static final String INVALID_INPUT_DATA = "15"; // Invalid input data (invalid format, invalid characters, or not enough data provided)

   public static final String INTERNAL_ERROR = "16"; // Internal hardware/software error

   public static final String INCOMPATIBLE_KEY_LENGTH = "27"; // Incompatible key length

   public static final String WRONG_IV_LENGTH = "28"; // Wrong initialization vector

   public static final String PRINTER_DISCONNECTED = "41";

   public static final String COMMAND_NOT_LICENSED = "67"; // 	Command not licensed

   public static final String COMMAND_DISABLED = "68";

   public static final String PIN_LENGTH_MISMATCH = "81";// PIN length mismatch

   public static final String PIN_IN_EXLUDED_TABLE = "86"; // PIN exists in either global or local Excluded PIN Table

   public static final String PGP_ENCRYPT_ERROR = "90";

   public static final String PGP_DECRYPT_ERROR = "91";

   public static final String UNKNOWN_ERROR = "99";

}
