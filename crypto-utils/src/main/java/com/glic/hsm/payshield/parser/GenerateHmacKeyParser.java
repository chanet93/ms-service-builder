package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.GenerateHmacKeyReq;
import com.glic.hsm.payshield.response.GenerateHmacKeyResp;

public class GenerateHmacKeyParser extends AbstractBaseResponseParser<GenerateHmacKeyReq, GenerateHmacKeyResp> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;

	@Override
	public GenerateHmacKeyResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		
		GenerateHmacKeyResp resp = new GenerateHmacKeyResp();

		if (ErrorsDef.NO_ERROR.equals(errorCode)) {

			String hmacKeyLengthStr = ParseUtils.getNextString(responseByteBuffer, 4, ASCII); 
			int hmacKeyLength = Integer.valueOf(hmacKeyLengthStr);

			byte[] hmacKey = ParseUtils.getNextBytes(responseByteBuffer, hmacKeyLength);
			resp.setHmacKey(hmacKey);
		}

		resp.setErrorCode(errorCode);
		return resp;
	}
}
