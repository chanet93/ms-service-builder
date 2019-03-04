package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.HsmStatsReq;
import com.glic.hsm.payshield.response.HsmStatsResp;

public class HsmStatResponseParser extends AbstractBaseResponseParser<HsmStatsReq, HsmStatsResp> {

	@Override
	public HsmStatsResp parseCommandResponse(ByteBuffer responseByteBuffer) {

		String errorCode = ParseUtils.getNextString(responseByteBuffer,COMMAND_CODE_LENGTH,ASCII);
    	
    	HsmStatsResp resp = new HsmStatsResp();
    	resp.setErrorCode(errorCode);
    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {
    	
    		String bufferSize = ParseUtils.getNextString(responseByteBuffer,1,ASCII);
    		String ethernetType = ParseUtils.getNextString(responseByteBuffer,1,ASCII);
    		String tcpSockets = ParseUtils.getNextString(responseByteBuffer,2,ASCII);
    		String firmwareNumber = ParseUtils.getNextString(responseByteBuffer,9,ASCII);

    		resp.setIoBufferSize(bufferSize);
    		resp.setEthernetType(ethernetType);
    		resp.setTcpSockets(tcpSockets);
    		resp.setFirmwareNumber(firmwareNumber);
    	}
    	
        return resp;
	}
}
