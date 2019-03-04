package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.ImportKeyReq;
import com.glic.hsm.payshield.response.ImportKeyResp;

/**
 * Created by erwine1 on 27/04/17.
 */
public class ImportKeyParser extends AbstractBaseResponseParser<ImportKeyReq, ImportKeyResp> {

   @Override
   public ImportKeyResp parseCommandResponse(ByteBuffer responseByteBuffer) {

      String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);

      ImportKeyResp response = new ImportKeyResp();
      response.setErrorCode(errorCode);

      if (ErrorsDef.NO_ERROR.equals(errorCode)) {

         String key = ParseUtils.getNextString(responseByteBuffer, 33, ASCII);
         String keyCheckValue = ParseUtils.getNextString(responseByteBuffer, 6, ASCII);

         response.setKeyUnderLMK(key);
         response.setKeyCheckValue(keyCheckValue);
      }

      return response;
   }

}
