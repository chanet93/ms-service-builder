package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.ValidateHmacReq;
import com.glic.hsm.payshield.response.ValidateHmacResp;

public class ValidateHmacParser extends AbstractBaseResponseParser<ValidateHmacReq, ValidateHmacResp> {

	@Override
	public ValidateHmacResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
    	ValidateHmacResp resp = new ValidateHmacResp();
    	resp.setErrorCode(errorCode);
        return resp;
	}
}
