package com.glic.hsm.payshield.utils;

import org.apache.commons.codec.binary.Hex;

import com.glic.hsm.payshield.exception.PaddingException;

public class Pkcs5Utils {

   /**
    * Unpad a byte array using pkcs5
    * 
    * @param paddedBlock
    * @return unpaded byte array
    */
   public static byte[] pkcs5Unpad(byte[] paddedBlock) throws PaddingException {
      int padLen = paddedBlock[(paddedBlock.length - 1)];
      if (padLen >= 1 && padLen <= 8) {
         int plainLen = paddedBlock.length - padLen;
         byte[] plainBlock = new byte[plainLen];
         System.arraycopy(paddedBlock, 0, plainBlock, 0, plainLen);
         return plainBlock;
      } else {
         throw new PaddingException("Cannot unpad the value " + Hex.encodeHexString(paddedBlock));
      }
   }

   /**
    * Pads a key byte using {@link Pkcs5Utils#pkcs5Pad(byte[], int) } and a
    * block size of 8
    * 
    * @see {@link Pkcs5Utils#pkcs5Pad(byte[], int) }
    * @param block
    * @return
    */
   public static byte[] pkcs5Pad(byte[] block) {
      return pkcs5Pad(block, 8);
   }

   /**
    * Pad a key byte array using pkcs5. The blockSize depends on the used
    * algorithm
    * 
    * @param block
    * @param blockSize
    * @return padded block
    */
   public static byte[] pkcs5Pad(byte[] block, int blockSize) {
      int padLen = blockSize - block.length % blockSize;
      if (padLen == 0) {
         padLen = blockSize;
      }
      int padVal = (byte) padLen;
      byte[] paddedBlock = new byte[block.length + padLen];
      System.arraycopy(block, 0, paddedBlock, 0, block.length);
      for (int k = 0; k < padLen; k++) {
         paddedBlock[(block.length + k)] = (byte) padVal;
      }
      return paddedBlock;
   }
}