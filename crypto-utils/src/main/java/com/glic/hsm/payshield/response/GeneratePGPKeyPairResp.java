package com.glic.hsm.payshield.response;

import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;

/**
 * Created by erwine1 on 08/09/16.
 */
public class GeneratePGPKeyPairResp extends HsmCommandResp {

   private PGPPublicKeyRing publicKeyRing;

   private PGPSecretKeyRing secretKeyRing;

   public PGPPublicKeyRing getPublicKeyRing() {
      return publicKeyRing;
   }

   public void setPublicKeyRing(PGPPublicKeyRing publicKeyRing) {
      this.publicKeyRing = publicKeyRing;
   }

   public PGPSecretKeyRing getSecretKeyRing() {
      return secretKeyRing;
   }

   public void setSecretKeyRing(PGPSecretKeyRing secretKeyRing) {
      this.secretKeyRing = secretKeyRing;
   }
}
