package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.RandomValueReq;
import com.glic.hsm.payshield.response.RandomValueResp;

public class RandomValueResponseParser extends AbstractBaseResponseParser<RandomValueReq, RandomValueResp> {

	@Override
	public RandomValueResp parseCommandResponse(ByteBuffer responseByteBuffer) {

		byte[] randomBytes = null;
		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		
    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {
    		randomBytes = ParseUtils.getNextBytes(responseByteBuffer, getRequestMessage().getSize()); 
    	}
    			
    	RandomValueResp resp = new RandomValueResp();
    	resp.setErrorCode(errorCode);
    	resp.setRandomBytes(randomBytes);
    	
        return resp;
	}
}
