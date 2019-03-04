package com.glic.hsm.payshield;

import java.util.logging.Logger;

import org.apache.commons.lang3.NotImplementedException;

import com.glic.hsm.HsmCommandFactory;
import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.CreateKeystoreReq;
import com.glic.hsm.nshield.response.CreateKeystoreResp;
import com.glic.hsm.payshield.connector.HsmConnector;
import com.glic.hsm.payshield.parser.CommandChainingParser;
import com.glic.hsm.payshield.parser.DecryptResponseParser;
import com.glic.hsm.payshield.parser.DecryptRsaResponseParser;
import com.glic.hsm.payshield.parser.EchoResponseParser;
import com.glic.hsm.payshield.parser.EncryptResponseParser;
import com.glic.hsm.payshield.parser.GenerateHmacKeyParser;
import com.glic.hsm.payshield.parser.GenerateHmacParser;
import com.glic.hsm.payshield.parser.GenerateKeyResponseParser;
import com.glic.hsm.payshield.parser.HashResponseParser;
import com.glic.hsm.payshield.parser.HsmStatResponseParser;
import com.glic.hsm.payshield.parser.ImportKeyParser;
import com.glic.hsm.payshield.parser.NetworkInformationResponseParser;
import com.glic.hsm.payshield.parser.PerformDiagnosticsResponseParser;
import com.glic.hsm.payshield.parser.RandomValueResponseParser;
import com.glic.hsm.payshield.parser.TranslateDataBlockParser;
import com.glic.hsm.payshield.parser.ValidateHmacParser;
import com.glic.hsm.payshield.renderer.CommandChainingRenderer;
import com.glic.hsm.payshield.renderer.DecryptRequestRenderer;
import com.glic.hsm.payshield.renderer.DecryptRsaRequestRenderer;
import com.glic.hsm.payshield.renderer.EchoRenderer;
import com.glic.hsm.payshield.renderer.EncryptRequestRenderer;
import com.glic.hsm.payshield.renderer.GenerateHmacKeyRenderer;
import com.glic.hsm.payshield.renderer.GenerateHmacRenderer;
import com.glic.hsm.payshield.renderer.GenerateKeyRequestRenderer;
import com.glic.hsm.payshield.renderer.HashRequestRenderer;
import com.glic.hsm.payshield.renderer.HsmStatRequestRenderer;
import com.glic.hsm.payshield.renderer.ImportKeyRenderer;
import com.glic.hsm.payshield.renderer.NetworkInformationRequestRenderer;
import com.glic.hsm.payshield.renderer.PerformDiagnosticsRequestRenderer;
import com.glic.hsm.payshield.renderer.RandomValueRequestRenderer;
import com.glic.hsm.payshield.renderer.TranslateDataBlockRenderer;
import com.glic.hsm.payshield.renderer.ValidateHmacRenderer;
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
import com.glic.hsm.payshield.request.ImportKeyReq;
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
import com.glic.hsm.payshield.response.ImportKeyResp;
import com.glic.hsm.payshield.response.NetworkInformationResp;
import com.glic.hsm.payshield.response.PerformDiagnosticsResp;
import com.glic.hsm.payshield.response.RandomValueResp;
import com.glic.hsm.payshield.response.TranslateDataBlockResp;
import com.glic.hsm.payshield.response.ValidateHmacResp;
import com.glic.hsm.payshield.simulator.SoftwareEncryptRsaCommand;

public class ThalesCommandFactory implements HsmCommandFactory {

   private static final Logger LOGGER = Logger.getLogger(ThalesCommandFactory.class.getName());

   private HsmConnector hsmConnector;

   @Override
   public void shutdown() {
      hsmConnector.close();
   }

   @Override
   public Command<EchoReq, EchoResp> createEchoCommand() {

      ThalesCommand<EchoReq, EchoResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new EchoRenderer());
      command.setResponseParser(new EchoResponseParser());

      return command;
   }

   @Override
   public Command<RandomValueReq, RandomValueResp> createRandomCommand() {

      ThalesCommand<RandomValueReq, RandomValueResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new RandomValueRequestRenderer());
      command.setResponseParser(new RandomValueResponseParser());

      return command;
   }

   @Override
   public Command<HashReq, HashResp> createHashCommand() {
      ThalesCommand<HashReq, HashResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new HashRequestRenderer());

      HashResponseParser responseParser = new HashResponseParser();
      responseParser.setMessageTrailerSize(hsmConnector.getMessageTrailer().length());
      command.setResponseParser(responseParser);

      return command;
   }

   @Override
   public Command<PerformDiagnosticsReq, PerformDiagnosticsResp> createPerformDiagnosticsCommand() {
      ThalesCommand<PerformDiagnosticsReq, PerformDiagnosticsResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new PerformDiagnosticsRequestRenderer());
      command.setResponseParser(new PerformDiagnosticsResponseParser());

      return command;
   }

   @Override
   public Command<GenerateKeyReq, GenerateKeyResp> createGenerateKeyCommand() {
      ThalesCommand<GenerateKeyReq, GenerateKeyResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new GenerateKeyRequestRenderer());
      command.setResponseParser(new GenerateKeyResponseParser());

      return command;
   }

   @Override
   public Command createGenerateHashingKeyCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);//TODO implement this command
   }

   @Override
   public Command<EncryptReq, EncryptResp> createEncryptCommand() {
      ThalesCommand<EncryptReq, EncryptResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new EncryptRequestRenderer());
      command.setResponseParser(new EncryptResponseParser());

      return command;
   }

   @Override
   public Command<DecryptReq, DecryptResp> createDecryptCommand() {
      ThalesCommand<DecryptReq, DecryptResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new DecryptRequestRenderer());
      command.setResponseParser(new DecryptResponseParser());

      return command;
   }

   @Override
   public Command<GenerateHmacKeyReq, GenerateHmacKeyResp> createGenerateHmacKeyCommand() {
      ThalesCommand<GenerateHmacKeyReq, GenerateHmacKeyResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new GenerateHmacKeyRenderer());
      command.setResponseParser(new GenerateHmacKeyParser());

      return command;
   }

   @Override
   public Command<GenerateHmacReq, GenerateHmacResp> createGenerateHmacCommand() {
      ThalesCommand<GenerateHmacReq, GenerateHmacResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new GenerateHmacRenderer());
      command.setResponseParser(new GenerateHmacParser());

      return command;
   }

   @Override
   public Command<ValidateHmacReq, ValidateHmacResp> createValidateHmacCommand() {
      ThalesCommand<ValidateHmacReq, ValidateHmacResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new ValidateHmacRenderer());
      command.setResponseParser(new ValidateHmacParser());

      return command;
   }

   @Override
   public Command<TranslateDataBlockReq, TranslateDataBlockResp> createTranslateDataBlockCommand() {
      ThalesCommand<TranslateDataBlockReq, TranslateDataBlockResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new TranslateDataBlockRenderer());
      command.setResponseParser(new TranslateDataBlockParser());

      return command;
   }

   @Override
   public Command<DecryptRsaReq, DecryptRsaResp> createDecryptRsaCommand() {
      ThalesCommand<DecryptRsaReq, DecryptRsaResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new DecryptRsaRequestRenderer());
      command.setResponseParser(new DecryptRsaResponseParser());
      return command;
   }

   @Override
   public Command<EncryptRsaReq, EncryptRsaResp> createEncryptRsaCommand() {
      LOGGER.warning("** Command using Thales HSM not implemented ** Using software implementation");
      return new SoftwareEncryptRsaCommand();
   }

   @Override
   public Command<CommandChainingReq, CommandChainingResp> createCommandChainingCommand() {
      ThalesCommand<CommandChainingReq, CommandChainingResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new CommandChainingRenderer());
      command.setResponseParser(new CommandChainingParser());

      return command;
   }

   @Override
   public Command<HsmStatsReq, HsmStatsResp> createHsmStatCommand() {
      ThalesCommand<HsmStatsReq, HsmStatsResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new HsmStatRequestRenderer());
      command.setResponseParser(new HsmStatResponseParser());
      return command;
   }

   @Override
   public Command<NetworkInformationReq, NetworkInformationResp> createNetworkInformationCommand() {
      ThalesCommand<NetworkInformationReq, NetworkInformationResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new NetworkInformationRequestRenderer());
      command.setResponseParser(new NetworkInformationResponseParser());
      return command;
   }

   @Override
   public Command createImportKey() {
      ThalesCommand<ImportKeyReq, ImportKeyResp> command = new ThalesCommand<>();
      setCommonProperties(command);
      command.setRequestRenderer(new ImportKeyRenderer());
      command.setResponseParser(new ImportKeyParser());
      return command;
   }

   @Override
   public Command<GeneratePGPKeyPairReq, GeneratePGPKeyPairResp> createPGPGeneratorCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command<EncryptPGPReq, EncryptPGPResp> createPGPEncryptCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command<DecryptPGPReq, DecryptPGPResp> createPGPDecryptCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
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
   public Command createGenerateAsymmetricKeyCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @SuppressWarnings("rawtypes")
   protected void setCommonProperties(ThalesCommand command) {
      command.setHsmConnector(hsmConnector);
   }

   public void setHsmConnector(HsmConnector hsmConnector) {
      this.hsmConnector = hsmConnector;
   }

}