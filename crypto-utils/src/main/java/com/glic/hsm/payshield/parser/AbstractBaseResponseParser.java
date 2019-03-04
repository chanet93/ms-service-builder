package com.glic.hsm.payshield.parser;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.PayshieldCommandReq;
import com.glic.hsm.payshield.response.HsmCommandResp;

@SuppressWarnings("all")
public abstract class AbstractBaseResponseParser<REQ extends PayshieldCommandReq, RESP extends HsmCommandResp> implements ResponseParser<REQ, RESP> {

	
	protected static final Charset ASCII = StandardCharsets.US_ASCII;
	public static final int COMMAND_CODE_LENGTH = 2;

	private REQ requestMessage;
	
	@Override
	public void setRequestMessage(REQ req) {
		this.requestMessage = req;
	}
	
	@Override
	public REQ getRequestMessage() {
		return requestMessage;
	}
	
	@Override
    public RESP createErrorResponse(String errorCode) {
    	RESP resp = getNewRESPInstance();
    	resp.setErrorCode(errorCode);
    	return resp;
    }

	private RESP getNewRESPInstance() {
		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		Class<RESP> type = (Class<RESP>) superClass.getActualTypeArguments()[1];
		try {
			return type.newInstance();
		} catch (Exception e) {
			// Oops, no default constructor
			throw new RuntimeException(e);
		}
	}
}
