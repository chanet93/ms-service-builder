package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.PerformDiagnosticsReq;
import com.glic.hsm.payshield.response.PerformDiagnosticsResp;

public class PerformDiagnosticsResponseParser extends AbstractBaseResponseParser<PerformDiagnosticsReq, PerformDiagnosticsResp> {

	@Override
	public PerformDiagnosticsResp parseCommandResponse(ByteBuffer responseByteBuffer) {

		String errorCode = ParseUtils.getNextString(responseByteBuffer,COMMAND_CODE_LENGTH,ASCII);
    	
    	PerformDiagnosticsResp resp = new PerformDiagnosticsResp();
    	resp.setErrorCode(errorCode);

    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {
    		
    		String lmkCheck =  ParseUtils.getNextString(responseByteBuffer,16,ASCII);
    		String firmwareNumber = ParseUtils.getNextString(responseByteBuffer,9,ASCII);
    		
    		resp.setLmkCheck(lmkCheck);
    		resp.setFirmwareNumber(firmwareNumber);
    	}
    	
        return resp;
	}
}
