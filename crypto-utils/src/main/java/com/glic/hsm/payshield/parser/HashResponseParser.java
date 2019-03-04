package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.HashReq;
import com.glic.hsm.payshield.response.HashResp;

public class HashResponseParser extends AbstractBaseResponseParser<HashReq, HashResp> {

	private static final int HASH_BYTES_OFFSET = 2;

	private int messageTrailerSize;
	
	@Override
	public HashResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
    	HashResp resp = new HashResp();

    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {

    		responseByteBuffer.position(HASH_BYTES_OFFSET);
    		int hashBytesLength = responseByteBuffer.remaining();
    		
    		if (messageTrailerSize != 0) {
    			hashBytesLength -= messageTrailerSize - 1; // includes end message delimiter (Value X'19)
    		}
    		
    		resp.setHashValue(new byte[hashBytesLength]);
    		responseByteBuffer.get(resp.getHashValue());
    	}
    			
    	resp.setErrorCode(errorCode);
    	
        return resp;
	}

	public void setMessageTrailerSize(int messageTrailerSize) {
		this.messageTrailerSize = messageTrailerSize;
	}
}
