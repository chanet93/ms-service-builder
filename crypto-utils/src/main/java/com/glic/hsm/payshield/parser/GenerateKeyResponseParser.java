package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.GenerateKeyReq;
import com.glic.hsm.payshield.response.GenerateKeyResp;

public class GenerateKeyResponseParser extends AbstractBaseResponseParser<GenerateKeyReq, GenerateKeyResp> {

	private static final int KEY_CHECK_VALUE = 6;
	
	@Override
	public GenerateKeyResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		GenerateKeyResp resp = new GenerateKeyResp();

		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		resp.setErrorCode(errorCode);
		
		if (ErrorsDef.NO_ERROR.equals(errorCode)) {
			
			// keyType (ignored) 
			String.valueOf((char)responseByteBuffer.get());
			
			byte[] keyBytes = ParseUtils.getNextBytes(responseByteBuffer, 
						responseByteBuffer.remaining() - KEY_CHECK_VALUE);
					
			String keyCheckValue = ParseUtils.getNextString(responseByteBuffer, 
					responseByteBuffer.remaining(), ASCII);

			resp.setKey(keyBytes);
			resp.setKeyCheckValue(keyCheckValue);
		}

		return resp;
	}
}
