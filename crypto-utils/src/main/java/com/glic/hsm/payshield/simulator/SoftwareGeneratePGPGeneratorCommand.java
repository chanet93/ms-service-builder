package com.glic.hsm.payshield.simulator;

import java.util.Objects;
import java.util.logging.Logger;

import org.bouncycastle.openpgp.PGPKeyRingGenerator;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.request.GeneratePGPKeyPairReq;
import com.glic.hsm.payshield.response.GeneratePGPKeyPairResp;

/**
 * Created by erwine1 on 08/09/16.
 */
public class SoftwareGeneratePGPGeneratorCommand implements Command<GeneratePGPKeyPairReq, GeneratePGPKeyPairResp> {

   private static final Logger LOGGER = Logger.getLogger(SoftwareCommandChainingCommand.class.getName());

   /**
    * @param req
    * @return
    * @throws HsmException
    */
   @Override
   public GeneratePGPKeyPairResp execute(GeneratePGPKeyPairReq req) throws HsmException {

      GeneratePGPKeyPairResp response = new GeneratePGPKeyPairResp();
      PGPKeyRingGenerator generator = new CryptoUtils().createRSAKeyRingGenerator(req.getUserId(), req.getPassword(), req.getKeySize());

      if (Objects.isNull(generator)) {
         response.setErrorCode(ErrorsDef.INTERNAL_ERROR);
         return response;
      }

      response.setErrorCode(ErrorsDef.NO_ERROR);
      response.setHeader(req.getHeader());
      response.setPublicKeyRing(generator.generatePublicKeyRing());
      response.setSecretKeyRing(generator.generateSecretKeyRing());
      return response;
   }
}
