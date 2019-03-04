package com.glic.hsm.nshield;

/**
 * Created by erwine1 on 16/12/16.
 */
public class NShieldErrorCode {


   public static final String NO_ERROR = "00";

   public static final String ILLEGAL_BLOCK_SIZE = "01"; // Bad block to decrypt

   public static final String BAD_PADDING = "02"; // Invalid padding

   public static final String NO_SUCH_PADDING = "03"; // Invalida padding type

   public static final String NO_SUCH_ALGORITHM = "04"; // Invalid Algorith

   public static final String NO_SUCH_PROVIDER = "05"; // Invalid provider

   public static final String EMPTY_OR_INVALID_DATA = "06"; // Invalid data

   public static final String INVALID_ALGORITHM_PARAMETER = "07"; // Invalid Algorith

   public static final String INVALID_KEY = "08"; // Invalid Algorith

   public static final String ERROR_PERSISTING_LOADING_KEYSTORE = "09"; // Invalid Algorith

   public static final String CERTIFICATE_ERROR = "10";

   public static final String KEYSTORE_ERROR = "11";
}
