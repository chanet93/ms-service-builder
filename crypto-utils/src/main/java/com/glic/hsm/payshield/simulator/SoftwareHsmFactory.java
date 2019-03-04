package com.glic.hsm.payshield.simulator;

import java.util.logging.Logger;

import org.apache.commons.lang3.NotImplementedException;

import com.glic.hsm.HsmCommandFactory;
import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.CreateKeystoreReq;
import com.glic.hsm.nshield.response.CreateKeystoreResp;
import com.glic.hsm.payshield.request.CommandChainingReq;
import com.glic.hsm.payshield.request.DecryptPGPReq;
import com.glic.hsm.payshield.request.DecryptReq;
import com.glic.hsm.payshield.request.DecryptRsaReq;
import com.glic.hsm.payshield.request.EchoReq;
import com.glic.hsm.payshield.request.EncryptPGPReq;
import com.glic.hsm.payshield.request.EncryptReq;
import com.glic.hsm.payshield.request.EncryptRsaReq;
import com.glic.hsm.payshield.request.GenerateHmacKeyReq;
import com.glic.hsm.payshield.request.GenerateHmacReq;
import com.glic.hsm.payshield.request.GenerateKeyReq;
import com.glic.hsm.payshield.request.GeneratePGPKeyPairReq;
import com.glic.hsm.payshield.request.HashReq;
import com.glic.hsm.payshield.request.HsmStatsReq;
import com.glic.hsm.payshield.request.NetworkInformationReq;
import com.glic.hsm.payshield.request.PerformDiagnosticsReq;
import com.glic.hsm.payshield.request.RandomValueReq;
import com.glic.hsm.payshield.request.TranslateDataBlockReq;
import com.glic.hsm.payshield.request.ValidateHmacReq;
import com.glic.hsm.payshield.response.CommandChainingResp;
import com.glic.hsm.payshield.response.DecryptPGPResp;
import com.glic.hsm.payshield.response.DecryptResp;
import com.glic.hsm.payshield.response.DecryptRsaResp;
import com.glic.hsm.payshield.response.EchoResp;
import com.glic.hsm.payshield.response.EncryptPGPResp;
import com.glic.hsm.payshield.response.EncryptResp;
import com.glic.hsm.payshield.response.EncryptRsaResp;
import com.glic.hsm.payshield.response.GenerateHmacKeyResp;
import com.glic.hsm.payshield.response.GenerateHmacResp;
import com.glic.hsm.payshield.response.GenerateKeyResp;
import com.glic.hsm.payshield.response.GeneratePGPKeyPairResp;
import com.glic.hsm.payshield.response.HashResp;
import com.glic.hsm.payshield.response.HsmStatsResp;
import com.glic.hsm.payshield.response.NetworkInformationResp;
import com.glic.hsm.payshield.response.PerformDiagnosticsResp;
import com.glic.hsm.payshield.response.RandomValueResp;
import com.glic.hsm.payshield.response.TranslateDataBlockResp;
import com.glic.hsm.payshield.response.ValidateHmacResp;

public class SoftwareHsmFactory implements HsmCommandFactory {

   private static final Logger LOGGER = Logger.getLogger(SoftwareHsmFactory.class.getName());

   @Override
   public void shutdown() {
      LOGGER.info("Nothing to do in shutdown by software");
   }

   @Override
   public Command<EchoReq, EchoResp> createEchoCommand() {
      return new SoftwareEchoCommand();
   }

   @Override
   public Command<RandomValueReq, RandomValueResp> createRandomCommand() {
      return new SoftwareRandomValueCommand();
   }

   @Override
   public Command<HashReq, HashResp> createHashCommand() {
      return new SoftwareHashCommand();
   }

   @Override
   public Command<EncryptReq, EncryptResp> createEncryptCommand() {
      return new SoftwareEncryptCommand();
   }

   @Override
   public Command<DecryptReq, DecryptResp> createDecryptCommand() {
      return new SoftwareDecryptCommand();
   }

   @Override
   public Command<TranslateDataBlockReq, TranslateDataBlockResp> createTranslateDataBlockCommand() {
      return new SoftwareTranslateDataBlockCommand();
   }

   @Override
   public Command<PerformDiagnosticsReq, PerformDiagnosticsResp> createPerformDiagnosticsCommand() {
      return new SoftwarePerformDiagnosticsCommand();
   }

   @Override
   public Command<GenerateKeyReq, GenerateKeyResp> createGenerateKeyCommand() {
      return new SoftwareGenerateKeyCommand();
   }

   @Override
   public Command createGenerateHashingKeyCommand() {
      return null;//TODO implement
   }

   @Override
   public Command<DecryptRsaReq, DecryptRsaResp> createDecryptRsaCommand() {
      return new SoftwareDecryptRsaCommand();
   }

   @Override
   public Command<EncryptRsaReq, EncryptRsaResp> createEncryptRsaCommand() {
      return new SoftwareEncryptRsaCommand();
   }

   @Override
   public Command<CommandChainingReq, CommandChainingResp> createCommandChainingCommand() {
      return new SoftwareCommandChainingCommand();
   }

   @Override
   public Command<GenerateHmacKeyReq, GenerateHmacKeyResp> createGenerateHmacKeyCommand() {
      return new SoftwareGenerateHmacKeyCommand();
   }

   @Override
   public Command<GenerateHmacReq, GenerateHmacResp> createGenerateHmacCommand() {
      return new SoftwareGenerateHmacCommand();
   }

   @Override
   public Command<ValidateHmacReq, ValidateHmacResp> createValidateHmacCommand() {
      return new SoftwareValidateHmacCommand();
   }

   @Override
   public Command<HsmStatsReq, HsmStatsResp> createHsmStatCommand() {
      return new SoftwareStatCommand();
   }

   @Override
   public Command<NetworkInformationReq, NetworkInformationResp> createNetworkInformationCommand() {
      return null;
   }

   @Override
   public Command<GeneratePGPKeyPairReq, GeneratePGPKeyPairResp> createPGPGeneratorCommand() {
      return new SoftwareGeneratePGPGeneratorCommand();
   }

   @Override
   public Command<EncryptPGPReq, EncryptPGPResp> createPGPEncryptCommand() {
      return new SoftwareEncryptPGPCommand();
   }

   @Override
   public Command<DecryptPGPReq, DecryptPGPResp> createPGPDecryptCommand() {
      return new SoftwareDecryptPGPCommand();
   }

   @Override
   public Command<CreateKeystoreReq, CreateKeystoreResp> createKeystoreCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command loadKeystoreCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command storeKeystoreCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createGenerateAsymmetricKeyCommand()  {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createImportKey() { throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);}

}
