package com.glic.hsm;

import com.glic.hsm.commons.Command;
import com.glic.hsm.commons.CommandReq;
import com.glic.hsm.commons.CommandResp;

/**
 * @author Chuck norris
 */
public interface HsmCommandFactory <request extends CommandReq, response extends CommandResp>{

   String COMMAND_NOT_IMPLEMENTED = "Command not implemented";

   void shutdown();


   Command<request,response> createEchoCommand();

   Command<request,response> createRandomCommand();

   Command<request,response> createHashCommand();

   Command<request,response> createDecryptCommand();

   Command<request,response> createEncryptCommand();

   Command<request,response> createTranslateDataBlockCommand();

   Command<request,response> createPerformDiagnosticsCommand();

   Command<request,response> createHsmStatCommand();

   Command<request,response> createGenerateKeyCommand();

   Command<request,response> createGenerateHashingKeyCommand();

   Command<request,response> createDecryptRsaCommand();

   Command<request,response> createEncryptRsaCommand();

   Command<request,response> createGenerateHmacKeyCommand();

   Command<request,response> createGenerateHmacCommand();

   Command<request,response> createValidateHmacCommand();

   Command<request,response> createCommandChainingCommand();

   Command<request,response> createNetworkInformationCommand();

   Command<request,response> createPGPGeneratorCommand();

   Command<request,response> createPGPEncryptCommand();

   Command<request,response> createPGPDecryptCommand();

   Command<request,response> createKeystoreCommand();

   Command<request,response> loadKeystoreCommand();

   Command<request,response> storeKeystoreCommand();

   Command<request,response> createGenerateAsymmetricKeyCommand();

   Command<request, response> createImportKey();
}