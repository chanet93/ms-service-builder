package com.glic.Generator;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

public enum EAlgorithm6 implements IGenerateAlgorithm{
   ;

   @Override
   public String generateAlgorithm(String bin, String branch, String cardType, String correlative)
         throws NoSuchProviderException, NoSuchAlgorithmException {

      String paddedBin = StringUtils.leftPad(bin, 6, "0");
      String paddedBranch = StringUtils.leftPad(branch, 2, "0");

      SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
      int randomInt = 1000000 + sr.nextInt(9000000);
      String randomStr = Integer.toString(randomInt);

      //aki obtenemos la tarjeta sin el numero de verificacion
      StringBuilder builder = new StringBuilder();
      builder.append(paddedBin);
      builder.append(paddedBranch);
      builder.append(randomStr);
      String concatenarCadenas = builder.toString();


      //covertimos a Int para efectuar la suma
      int concatenarCadenasI = Integer.parseInt(concatenarCadenas);

      //se efectua la suma
      int checkDigit = LuhnCheckDigit.sumDigits(concatenarCadenasI);

      //Se covierte a string para concatenarlo y finalemnte generar la tarjeta
      String checkDigitS= Integer.toString(checkDigit);


      String generarTarjeta = concatenarCadenas + checkDigitS;

       return generarTarjeta;


         }
      }

