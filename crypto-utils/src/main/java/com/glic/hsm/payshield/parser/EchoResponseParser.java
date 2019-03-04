package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.EchoReq;
import com.glic.hsm.payshield.response.EchoResp;

public class EchoResponseParser extends AbstractBaseResponseParser<EchoReq, EchoResp> {

	public EchoResp parseCommandResponse(ByteBuffer responseByteBuffer) {
        
    	EchoResp resp = new EchoResp();
    	
    	String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
    	resp.setErrorCode(errorCode);

    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {
    		int messageSize  = getRequestMessage().getMessageData().length();
    		String messageData = ParseUtils.getNextString(responseByteBuffer, messageSize, ASCII); 
    		resp.setMessageData(messageData);
    	}
        return resp;
    }
}