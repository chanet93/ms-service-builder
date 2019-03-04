package com.glic.hsm.payshield.simulator;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.EchoReq;
import com.glic.hsm.payshield.response.EchoResp;

public class SoftwareEchoCommand implements Command<EchoReq, EchoResp> {

	@Override
	public EchoResp execute(EchoReq req) {

		EchoResp resp = new EchoResp();
		resp.setErrorCode(ErrorsDef.NO_ERROR);
		resp.setMessageData(req.getMessageData());

		return resp;
	}
}
