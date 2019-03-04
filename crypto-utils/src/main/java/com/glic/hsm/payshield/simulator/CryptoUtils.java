package com.glic.hsm.payshield.simulator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.util.io.Streams;

import com.squareup.crypto.rsa.NativeRSAEngine;

import com.glic.hsm.payshield.HashAlgorithm;
import com.glic.hsm.payshield.enums.EncryptionAlgorithmEnum;
import com.glic.hsm.payshield.enums.EncryptionModeEnum;

/**
 * @author Chuck Norris
 */
public class CryptoUtils {

   private static final Logger LOGGER = Logger.getLogger(CryptoUtils.class.getName());

   public static final String BC = "BC";

   public static final int FERMAT_NUMBER = 0x10001;

   /**
    * @param data
    * @param key
    * @param iv
    * @param encryptionModeEnum
    * @param encryptionAlgorithmEnum
    * @return
    * @throws InvalidKeyException
    * @throws InvalidAlgorithmParameterException
    * @throws IllegalBlockSizeException
    * @throws BadPaddingException
    */
   public byte[] decryptData(byte[] data, byte[] key, byte[] iv, EncryptionModeEnum encryptionModeEnum, EncryptionAlgorithmEnum encryptionAlgorithmEnum)
         throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
      SecretKey secretKey = new SecretKeySpec(key, encryptionAlgorithmEnum.getCommandValue());
      AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);

      Cipher cipher = null;
      try {
         cipher = Cipher.getInstance(encryptionAlgorithmEnum.getCommandValue() + "/" + encryptionModeEnum + "/PKCS5Padding");
      } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
         LOGGER.log(Level.SEVERE, "Exception thrown", e);
         throw new RuntimeException(e);
      }

      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
      byte[] resultBytes = cipher.doFinal(data);

      return resultBytes;
   }

   /**
    * @param data
    * @param key
    * @param iv
    * @param encryptionModeEnum
    * @param encryptionAlgorithmEnum
    * @return
    * @throws InvalidKeyException
    * @throws InvalidAlgorithmParameterException
    * @throws IllegalBlockSizeException
    * @throws BadPaddingException
    */
   public byte[] encryptData(byte[] data, byte[] key, byte[] iv, EncryptionModeEnum encryptionModeEnum, EncryptionAlgorithmEnum encryptionAlgorithmEnum)
         throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
      SecretKey secretKey = new SecretKeySpec(key, encryptionAlgorithmEnum.getCommandValue());
      AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);

      Cipher cipher = null;
      try {
         cipher = Cipher.getInstance(encryptionAlgorithmEnum.getCommandValue() + "/" + encryptionModeEnum + "/PKCS5Padding");
      } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
         LOGGER.log(Level.SEVERE, "Exception thrown", e);
         throw new RuntimeException(e);
      }

      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
      byte[] resultBytes = cipher.doFinal(data);

      return resultBytes;
   }

   /**
    * @param encryptedData
    * @param privateKeyBytes
    * @return
    * @throws IOException
    * @throws InvalidCipherTextException
    */
   public byte[] decryptRsa(byte[] encryptedData, byte[] privateKeyBytes) throws IOException, InvalidCipherTextException {
      AsymmetricKeyParameter privateKeyParam = PrivateKeyFactory.createKey(privateKeyBytes);

      RSAEngine cipher = null;
      if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC) {
         cipher = new NativeRSAEngine();
      } else {
         cipher = new RSAEngine();
      }

      AsymmetricBlockCipher e = new PKCS1Encoding(cipher);

      e.init(false, privateKeyParam);
      byte[] decryptedData = e.processBlock(encryptedData, 0, encryptedData.length);
      return decryptedData;
   }

   /**
    * @param clearData
    * @param publicKeyBytes
    * @return
    * @throws IOException
    * @throws InvalidCipherTextException
    */
   public byte[] encryptRsa(byte[] clearData, byte[] publicKeyBytes) throws IOException, InvalidCipherTextException {
      AsymmetricKeyParameter publicKeyParam = PublicKeyFactory.createKey(publicKeyBytes);

      RSAEngine cipher = null;
      if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC) {
         cipher = new NativeRSAEngine();
      } else {
         cipher = new RSAEngine();
      }

      AsymmetricBlockCipher e = new PKCS1Encoding(cipher);

      e.init(true, publicKeyParam);
      byte[] encryptedData = e.processBlock(clearData, 0, clearData.length);
      return encryptedData;
   }

   /**
    * @param hashAlgorithm
    * @return
    */
   public String getHmacAlgorithm(HashAlgorithm hashAlgorithm) {
      String hmacAlgorithm = null;

      switch (hashAlgorithm) {
         case MD5:
            hmacAlgorithm = "HmacMD5";
            break;
         case SHA_1:
            hmacAlgorithm = "HmacSHA1";
            break;
         case SHA_224:
            hmacAlgorithm = "HmacSHA224";
            break;
         case SHA_256:
            hmacAlgorithm = "HmacSHA256";
            break;
         case SHA_384:
            hmacAlgorithm = "HmacSHA384";
            break;
         case SHA_512:
            hmacAlgorithm = "HmacSHA512";
            break;
         default:
            break;
      }

      return hmacAlgorithm;
   }

   /**
    * @param userId
    * @param password
    * @param keySize
    * @return RSA + SHA512 + ZIP + AES_256 key pair generator
    */
   public PGPKeyRingGenerator createRSAKeyRingGenerator(String userId, String password, int keySize) {
      PGPKeyRingGenerator generator = null;
      try {
         RSAKeyPairGenerator generator1 = new RSAKeyPairGenerator();
         generator1.init(new RSAKeyGenerationParameters(BigInteger.valueOf(FERMAT_NUMBER), new SecureRandom(), keySize, 12));

         BcPGPKeyPair signingKeyPair = new BcPGPKeyPair(PGPPublicKey.RSA_GENERAL, generator1.generateKeyPair(), new Date());

         BcPGPKeyPair encryptionKeyPair = new BcPGPKeyPair(PGPPublicKey.RSA_ENCRYPT, generator1.generateKeyPair(), new Date());

         PGPSignatureSubpacketGenerator signatureSubpacketGenerator = new PGPSignatureSubpacketGenerator();

         signatureSubpacketGenerator.setKeyFlags(false, KeyFlags.SIGN_DATA | KeyFlags.CERTIFY_OTHER);
         signatureSubpacketGenerator.setPreferredSymmetricAlgorithms(false, new int[] { SymmetricKeyAlgorithmTags.AES_256 });
         signatureSubpacketGenerator.setPreferredHashAlgorithms(false, new int[] { HashAlgorithmTags.SHA512 });
         signatureSubpacketGenerator.setPreferredCompressionAlgorithms(false, new int[] { CompressionAlgorithmTags.ZIP });

         PGPSignatureSubpacketGenerator encryptionSubpacketGenerator = new PGPSignatureSubpacketGenerator();

         encryptionSubpacketGenerator.setKeyFlags(false, KeyFlags.ENCRYPT_COMMS | KeyFlags.ENCRYPT_STORAGE);

         generator = new PGPKeyRingGenerator(PGPPublicKey.RSA_GENERAL, signingKeyPair, userId,
               new BcPGPDigestCalculatorProvider().get(HashAlgorithmTags.SHA1), signatureSubpacketGenerator.generate(), null,
               new BcPGPContentSignerBuilder(PGPPublicKey.RSA_SIGN, HashAlgorithmTags.SHA512),
               new BcPBESecretKeyEncryptorBuilder(PGPEncryptedData.AES_256).build(password.toCharArray()));
         generator.addSubKey(encryptionKeyPair, encryptionSubpacketGenerator.generate(), null);
      } catch (PGPException e) {
         LOGGER.log(Level.SEVERE,"Error creating the PGP key generator" , e);
         generator = null;
      }
      return generator;
   }

   /**
    * @param secret
    * @param keys
    * @return
    * @throws IOException
    * @throws PGPException
    */
   public byte[] encryptPGP(final byte[] secret, final PGPPublicKey...keys) throws IOException, PGPException {

      byte[] bytes = null;
      final PGPLiteralDataGenerator literal = new PGPLiteralDataGenerator();
      final PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(CompressionAlgorithmTags.ZIP);
      OutputStream pOut = null;

      try(ByteArrayInputStream in = new ByteArrayInputStream(secret);ByteArrayOutputStream bOut = new ByteArrayOutputStream();){
         try{
            pOut = literal.open(comData.open(bOut), PGPLiteralData.BINARY, "filename", in.available(), new Date());
            Streams.pipeAll(in, pOut);
         }finally {
            if(Objects.nonNull(comData)){
               comData.close();
            }
            if(Objects.nonNull(literal)){
               literal.close();
            }
            if(Objects.nonNull(pOut)){
               pOut.close();
            }
         }
         bytes = bOut.toByteArray();
      }

      final PGPEncryptedDataGenerator generator = getGenerator(keys);

      try(ByteArrayOutputStream out = new ByteArrayOutputStream();ArmoredOutputStream armor = new ArmoredOutputStream(out);OutputStream cOut = generator.open(armor, bytes.length);){
         cOut.write(bytes);
         cOut.close();
         armor.close();
         bytes = out.toByteArray();
      }

      return bytes;
   }

   /**
    *
    * @param keys
    * @return
    */
   private PGPEncryptedDataGenerator getGenerator(PGPPublicKey[] keys) {
      PGPEncryptedDataGenerator generator = new PGPEncryptedDataGenerator(
            new JcePGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256).setWithIntegrityPacket(true).setSecureRandom(
                  new SecureRandom()).setProvider(BC));
      for (final PGPPublicKey key : keys) {
         generator.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(key).setProvider(BC));
      }
      return generator;
   }

   /**
    * @param data
    * @param privateKey
    * @param passphrase
    * @return
    * @throws IOException
    * @throws PGPException
    */
   public byte[] decryptPGP(final byte[] data, final InputStream privateKey, final String passphrase) throws IOException, PGPException {
      PGPLiteralData message = asLiteralPGP(data, privateKey, passphrase);
      try(ByteArrayOutputStream out = new ByteArrayOutputStream();){
         Streams.pipeAll(message.getInputStream(), out);
         return out.toByteArray();
      }
   }

   /**
    * @param data
    * @param keyfile
    * @param passphrase
    * @return
    * @throws IOException
    * @throws PGPException
    */
   private PGPLiteralData asLiteralPGP(final byte[] data, final InputStream keyfile, final String passphrase) throws IOException, PGPException {
      PGPPrivateKey key = null;
      PGPPublicKeyEncryptedData encrypted = null;
      PGPSecretKeyRingCollection keys = new PGPSecretKeyRingCollection(new ArmoredInputStream(keyfile), new JcaKeyFingerprintCalculator());
      for (final Iterator<PGPPublicKeyEncryptedData> i = getEncryptedObjects(data); (key == null) && i.hasNext(); ) {
         encrypted = i.next();
         key = findSecretKey(keys, encrypted.getKeyID(), passphrase);
      }
      if (key == null) {
         throw new IllegalArgumentException("secret key for message not found.");
      }
      InputStream stream = encrypted.getDataStream(new JcePublicKeyDataDecryptorFactoryBuilder().setProvider(BC).build(key));
      return asLiteralPGP(stream);
   }

   /**
    * @param clear
    * @return
    * @throws IOException
    * @throws PGPException
    */
   private PGPLiteralData asLiteralPGP(final InputStream clear) throws IOException, PGPException {
      PGPObjectFactory plainFact = new PGPObjectFactory(clear, new JcaKeyFingerprintCalculator());
      Object message = plainFact.nextObject();
      if (message instanceof PGPCompressedData) {
         PGPCompressedData cData = (PGPCompressedData) message;
         PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream(), new JcaKeyFingerprintCalculator());
         // Find the first PGPLiteralData object
         Object object = null;
         for (int safety = 0; (safety++ < 1000) && !(object instanceof PGPLiteralData); object = pgpFact.nextObject()) {
            ;
         }
         return (PGPLiteralData) object;
      } else if (message instanceof PGPLiteralData) {
         return (PGPLiteralData) message;
      } else if (message instanceof PGPOnePassSignatureList) {
         throw new PGPException("encrypted message contains a signed message - not literal data.");
      } else {
         throw new PGPException("message is not a simple encrypted file - type unknown: " + message.getClass().getName());
      }
   }

   /**
    * @param data
    * @return
    * @throws IOException
    */
   private Iterator<PGPPublicKeyEncryptedData> getEncryptedObjects(final byte[] data) throws IOException {
      PGPObjectFactory factory = new PGPObjectFactory(PGPUtil.getDecoderStream(new ByteArrayInputStream(data)), new JcaKeyFingerprintCalculator());
      Object first = factory.nextObject();
      Object list = (first instanceof PGPEncryptedDataList) ? first : factory.nextObject();
      return ((PGPEncryptedDataList) list).getEncryptedDataObjects();
   }

   /**
    * @param keys
    * @param id
    * @param passphrase
    * @return
    */
   private PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection keys, long id, String passphrase) throws PGPException {
      if (StringUtils.isEmpty(passphrase) || Objects.isNull(keys)) {
         throw new PGPException("Arguments keys , id , passphrase are required");
      }
      try {
         PGPSecretKey key = keys.getSecretKey(id);
         if (Objects.nonNull(key)) {
            return key.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider(BC).build(passphrase.toCharArray()));
         }
      } catch (Exception e) {
         LOGGER.log(Level.SEVERE, "Cannot extract private key",e);
      }
      return null;
   }

}
