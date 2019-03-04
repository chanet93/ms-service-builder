package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.GenerateHmacReq;
import com.glic.hsm.payshield.response.GenerateHmacResp;

public class GenerateHmacParser extends AbstractBaseResponseParser<GenerateHmacReq, GenerateHmacResp> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public GenerateHmacResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		
    	GenerateHmacResp resp = new GenerateHmacResp();

    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {

			String hmacLengthStr = ParseUtils.getNextString(responseByteBuffer, 4, ASCII); 
			int hmacLength = Integer.valueOf(hmacLengthStr);

    		byte[] hmac = ParseUtils.getNextBytes(responseByteBuffer, hmacLength);
    		resp.setHmac(hmac);
    	}
    			
    	resp.setErrorCode(errorCode);
        return resp;
	}
}
