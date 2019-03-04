package com.glic.hsm.nshield;

import java.util.logging.Logger;

import org.apache.commons.lang3.NotImplementedException;

import com.glic.hsm.HsmCommandFactory;
import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.CommandChainingReq;
import com.glic.hsm.nshield.request.DecryptReq;
import com.glic.hsm.nshield.request.EchoReq;
import com.glic.hsm.nshield.request.EncryptReq;
import com.glic.hsm.nshield.request.GenerateHmacReq;
import com.glic.hsm.nshield.request.GenerateSymmetricKeyReq;
import com.glic.hsm.nshield.request.HashReq;
import com.glic.hsm.nshield.request.RandomValueReq;
import com.glic.hsm.nshield.request.ValidateHmacReq;
import com.glic.hsm.nshield.response.CommandChainingResp;
import com.glic.hsm.nshield.response.DecryptResp;
import com.glic.hsm.nshield.response.EchoResp;
import com.glic.hsm.nshield.response.EncryptResp;
import com.glic.hsm.nshield.response.GenerateHmacResp;
import com.glic.hsm.nshield.response.GenerateSymmetricKeyResp;
import com.glic.hsm.nshield.response.HashResp;
import com.glic.hsm.nshield.response.RandomValueResp;
import com.glic.hsm.nshield.response.ValidateHmacResp;

/**
 * @author erwine1
 */
public class NShieldHsmFactory implements HsmCommandFactory {

   private static final Logger LOGGER = Logger.getLogger(NShieldHsmFactory.class.getName());

   public final static String SUN_PROVIDER = null;

   public final static String NSHIELD_PROVIDER = "nCipherKM";

   private String provider;

   @Override
   public void shutdown() {
      LOGGER.info("Nothing to do in shutdown by nshield");
   }

   @Override
   public Command<EchoReq, EchoResp> createEchoCommand() {
      EchoCommand echoCommand = new EchoCommand();
      echoCommand.setProvider(provider);
      return echoCommand;
   }

   @Override
   public Command<RandomValueReq, RandomValueResp> createRandomCommand() {
      RandomValueCommand nShieldRandomValueCommand = new RandomValueCommand();
      nShieldRandomValueCommand.setProvider(provider);
      return nShieldRandomValueCommand;
   }

   @Override
   public Command<HashReq, HashResp> createHashCommand() {
      HashCommand nShieldHashCommand = new HashCommand();
      nShieldHashCommand.setProvider(provider);
      return nShieldHashCommand;
   }

   @Override
   public Command<EncryptReq, EncryptResp> createEncryptCommand() {
      EncryptCommand nShieldEncryptCommand = new EncryptCommand();
      nShieldEncryptCommand.setProvider(provider);
      return nShieldEncryptCommand;
   }

   @Override
   public Command<DecryptReq, DecryptResp> createDecryptCommand() {
      DecryptCommand nShieldDecryptCommand = new DecryptCommand();
      nShieldDecryptCommand.setProvider(provider);
      return nShieldDecryptCommand;
   }

   @Override
   public Command<GenerateSymmetricKeyReq, GenerateSymmetricKeyResp> createGenerateKeyCommand() {
      GenerateSymmetricKeyCommand nShieldGenerateSymmetricKeyCommand = new GenerateSymmetricKeyCommand();
      nShieldGenerateSymmetricKeyCommand.setProvider(provider);
      return nShieldGenerateSymmetricKeyCommand;
   }

   @Override
   public Command createGenerateHashingKeyCommand() {
      GenerateHashingKeyCommand nShieldGenerateHashingKeyCommand = new GenerateHashingKeyCommand();
      nShieldGenerateHashingKeyCommand.setProvider(provider);
      return nShieldGenerateHashingKeyCommand;
   }

   @Override
   public Command<CommandChainingReq, CommandChainingResp> createCommandChainingCommand() {
      CommandChainingCommand commandChainingCommand = new CommandChainingCommand();
      commandChainingCommand.setProvider(provider);
      return commandChainingCommand;
   }

   @Override
   public Command<GenerateHmacReq, GenerateHmacResp> createGenerateHmacCommand() {
      GenerateHmacCommand generateHmacCommand = new GenerateHmacCommand();
      generateHmacCommand.setProvider(provider);
      return generateHmacCommand;
   }

   @Override
   public Command<ValidateHmacReq, ValidateHmacResp> createValidateHmacCommand() {
      ValidateHmacCommand validateHmacCommand = new ValidateHmacCommand();
      validateHmacCommand.setProvider(provider);
      return validateHmacCommand;
   }

   @Override
   public Command createKeystoreCommand() {
      CreateKeystoreCommand createKeystoreCommand = new CreateKeystoreCommand();
      createKeystoreCommand.setProvider(provider);
      return createKeystoreCommand;
   }

   @Override
   public Command loadKeystoreCommand() {
      LoadKeystoreCommand loadKeystoreCommand = new LoadKeystoreCommand();
      loadKeystoreCommand.setProvider(provider);
      return loadKeystoreCommand;
   }

   @Override
   public Command storeKeystoreCommand() {
      StoreKeystoreCommand storeKeystoreCommand = new StoreKeystoreCommand();
      storeKeystoreCommand.setProvider(provider);
      return storeKeystoreCommand;
   }

   @Override
   public Command createGenerateAsymmetricKeyCommand() {
      GenerateAsymmetricKeyCommand generateSymmetricKeyCommand = new GenerateAsymmetricKeyCommand();
      generateSymmetricKeyCommand.setProvider(provider);
      return generateSymmetricKeyCommand;
   }


   @Override
   public Command createGenerateHmacKeyCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createHsmStatCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createNetworkInformationCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createPGPGeneratorCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createPGPEncryptCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createPGPDecryptCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createPerformDiagnosticsCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createTranslateDataBlockCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   @Override
   public Command createImportKey() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   /**
    * This method will be sustituted by decrypt asymmetric
    *
    * @return
    */
   @Deprecated
   @Override
   public Command createDecryptRsaCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   /**
    * This method will be sustituted by encrypt asymmetric
    *
    * @return
    */
   @Deprecated
   @Override
   public Command createEncryptRsaCommand() {
      throw new NotImplementedException(COMMAND_NOT_IMPLEMENTED);
   }

   public void setProvider(String provider) {
      this.provider = provider;
   }

   public String getProvider() {
      return provider;
   }
}
