package com.glic.hsm.nshield.utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;

/**
 *
 */
public final class KeyUtil {

   /**
    * @param input
    * @return
    * @throws IOException
    * @throws PGPException
    */
   public static PGPPublicKey findPublicKey(final InputStream input) throws IOException, PGPException {
      return findKey(new PublicKey(input));
   }

   /**
    * @param input
    * @return
    * @throws IOException
    * @throws PGPException
    */
   public static PGPSecretKey findSecretKey(final InputStream input) throws IOException, PGPException {
      return findKey(new PrivateKey(input));
   }


   /**
    * @param input
    * @return
    * @throws IOException
    * @throws PGPException
    */
   public static PGPSecretKey findSecretKey(final byte[] input) throws IOException, PGPException {
      try(InputStream is = new ByteArrayInputStream(input);){
         return findKey(new PrivateKey(is));
      }
   }

   /**
    * @param input
    * @return
    * @throws IOException
    * @throws PGPException
    */
   public static PGPPublicKey findPublicKeyFromPrivate(final InputStream input) throws IOException, PGPException {
      return findKey(new PublicKeyFromPrivate(input));
   }

   /**
    * @param reader
    * @param <T>
    * @return
    */
   @SuppressWarnings("unchecked")
   private static <T> T findKey(final KeyReader reader) {
      try {
         final Iterator<?> rings = reader.getKeyRings();
         while (rings.hasNext()) {
            final Iterator<?> keys = reader.getKeys(rings);
            while (keys.hasNext()) {
               final Object key = keys.next();
               if (reader.isValid(key)) {
                  return (T) key;
               }
            }
         }
         throw new IllegalArgumentException("Can’t find encryption key using: " + reader);
      } finally {
         reader.close();
      }
   }

   /**
    *
    */
   private static abstract class KeyReader {

      protected final InputStream input;

      public abstract Iterator<?> getKeyRings();

      public abstract Iterator<?> getKeys(Iterator<?> keyRings);

      public abstract boolean isValid(Object key);

      public KeyReader(final InputStream input) throws FileNotFoundException, IOException {
         this.input = PGPUtil.getDecoderStream(input);
      }

      public final void close() {
         try {
            this.input.close();
         } catch (final IOException e) {
            e.printStackTrace();
         }
      }

      @Override
      public final String toString() {
         return getClass().getSimpleName();
      }
   }

   /**
    *
    */
   private static final class PublicKey extends KeyReader {

      private final PGPPublicKeyRingCollection keyring;

      public PublicKey(final InputStream input) throws IOException, PGPException {
         super(input);
         this.keyring = new PGPPublicKeyRingCollection(this.input, new JcaKeyFingerprintCalculator());
      }

      @Override
      public Iterator<?> getKeyRings() {
         return this.keyring.getKeyRings();
      }

      @Override
      public Iterator<?> getKeys(final Iterator<?> keyRings) {
         return ((PGPPublicKeyRing) keyRings.next()).getPublicKeys();
      }

      @Override
      public boolean isValid(final Object key) {
         return ((PGPPublicKey) key).isEncryptionKey();
      }
   }

   /**
    *
    */
   private static class PrivateKey extends KeyReader {

      private final PGPSecretKeyRingCollection keyring;

      public PrivateKey(final InputStream input) throws IOException, PGPException {
         super(input);
         this.keyring = new PGPSecretKeyRingCollection(this.input, new JcaKeyFingerprintCalculator());
      }

      @Override
      public Iterator<?> getKeyRings() {
         return this.keyring.getKeyRings();
      }

      @Override
      public Iterator<?> getKeys(final Iterator<?> keyRings) {
         return ((PGPSecretKeyRing) keyRings.next()).getSecretKeys();
      }

      @Override
      public boolean isValid(final Object key) {
         return ((PGPSecretKey) key).isSigningKey();
      }
   }

   /**
    *
    */
   private static final class PublicKeyFromPrivate extends PrivateKey {

      public PublicKeyFromPrivate(final InputStream input) throws IOException, PGPException {
         super(input);
      }

      @Override
      public Iterator<?> getKeys(final Iterator<?> keyRings) {
         return ((PGPSecretKeyRing) keyRings.next()).getPublicKeys();
      }

      @Override
      public boolean isValid(final Object key) {
         return ((PGPPublicKey) key).isEncryptionKey();
      }
   }
}