package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.commons.CommandReq;
import com.glic.hsm.commons.CommandResp;

public interface ResponseParser<REQ extends CommandReq, RESP extends CommandResp> {

	public RESP parseCommandResponse(ByteBuffer responseByteBuffer);
    
	public RESP createErrorResponse(String errorCode);
	
	public void setRequestMessage(REQ req);
	
	public REQ getRequestMessage();
}
