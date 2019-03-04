package com.glic.hsm.nshield;

import static com.glic.hsm.nshield.AbstractCommand.NCIPHER_SWORLD_KEYSTORE_PROVIDER;

import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;

import com.glic.hsm.nshield.enums.EncryptionModeEnum;
import com.glic.hsm.nshield.enums.EncryptionTypeEnum;
import com.glic.hsm.nshield.enums.HashTypeEnum;
import com.glic.hsm.nshield.enums.PaddingTypeEnum;
import com.glic.hsm.nshield.enums.RandomAlgorythmEnum;

/**
 * Created by erwine1 on 19/01/17.
 */
public class CryptoUtils {


   /**
    *
    * @param provider
    * @return
    * @throws NoSuchProviderException
    * @throws KeyStoreException
    */
   protected static KeyStore getKeystoreByProvider(String provider) throws NoSuchProviderException, KeyStoreException {
      if(Objects.nonNull(provider)){
        return KeyStore.getInstance(NCIPHER_SWORLD_KEYSTORE_PROVIDER, provider);
      }else{
         return KeyStore.getInstance(KeyStore.getDefaultType());
      }
   }

   /**
    *
    * @param provider
    * @param encryptionTypeEnum
    * @param encryptionMode
    * @param paddingTypeEnum
    * @return
    * @throws NoSuchPaddingException
    * @throws NoSuchAlgorithmException
    * @throws NoSuchProviderException
    */
   protected static Cipher getCipherByProvider(String provider,EncryptionTypeEnum encryptionTypeEnum,EncryptionModeEnum encryptionMode,PaddingTypeEnum paddingTypeEnum)
         throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
      if(Objects.nonNull(provider)){
         return Cipher.getInstance(encryptionTypeEnum.alias() + "/" + encryptionMode.getAlias() + "/" + paddingTypeEnum.getPadding(),
               provider);
      }else{
         return Cipher.getInstance(encryptionTypeEnum.alias() + "/" + encryptionMode.getAlias() + "/" + paddingTypeEnum.getPadding());
      }
   }

   /**
    *
    * @param provider
    * @param encryptionTypeEnum
    * @return
    * @throws NoSuchProviderException
    * @throws NoSuchAlgorithmException
    */
   protected static KeyPairGenerator getKeyPairGeneratorByProvider(String provider,EncryptionTypeEnum encryptionTypeEnum)
         throws NoSuchProviderException, NoSuchAlgorithmException {
      if(Objects.nonNull(provider)){
         return KeyPairGenerator.getInstance(encryptionTypeEnum.alias(), provider);
      }else{
         return KeyPairGenerator.getInstance(encryptionTypeEnum.alias());
      }
   }

   /**
    *
    * @param provider
    * @param encryptionTypeEnum
    * @return
    * @throws NoSuchProviderException
    * @throws NoSuchAlgorithmException
    */
   public static KeyGenerator getKeyGeneratorByProvider(String provider, EncryptionTypeEnum encryptionTypeEnum)
         throws NoSuchProviderException, NoSuchAlgorithmException {
      if(Objects.nonNull(provider)){
         return  KeyGenerator.getInstance(encryptionTypeEnum.alias(), provider);
      }else{
         return KeyGenerator.getInstance(encryptionTypeEnum.alias());
      }
   }


   /**
    *
    * @param provider
    * @param algorithmType
    * @return
    * @throws NoSuchProviderException
    * @throws NoSuchAlgorithmException
    */
   public static KeyGenerator getKeyGeneratorByProvider(String provider, HashTypeEnum algorithmType)
         throws NoSuchProviderException, NoSuchAlgorithmException {
      if(Objects.nonNull(provider)){
         return  KeyGenerator.getInstance(algorithmType.alias(), provider);
      }else{
         return KeyGenerator.getInstance(algorithmType.alias());
      }
   }


   /**
    *
    * @param provider
    * @param algorithmType
    * @return
    * @throws NoSuchProviderException
    * @throws NoSuchAlgorithmException
    */
   public static Mac getMacByProvider(String provider,HashTypeEnum algorithmType) throws NoSuchProviderException, NoSuchAlgorithmException {
      Mac mac;
      if(Objects.nonNull(provider)){
        return Mac.getInstance(algorithmType.alias(),provider);
      }else{
         return Mac.getInstance(algorithmType.alias());
      }
   }

   /**
    *
    * @param provider
    * @param sha1prng
    * @return
    * @throws NoSuchProviderException
    * @throws NoSuchAlgorithmException
    */
   public static SecureRandom getSecureRandomByProvider(String provider, RandomAlgorythmEnum sha1prng)
         throws NoSuchProviderException, NoSuchAlgorithmException {
      if(Objects.nonNull(provider)){
         return SecureRandom.getInstance(sha1prng.getAlias(), provider);
      }else{
         return SecureRandom.getInstance(sha1prng.getAlias());
      }
   }
}
