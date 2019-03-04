package com.glic.hsm.payshield.simulator;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.PerformDiagnosticsReq;
import com.glic.hsm.payshield.response.PerformDiagnosticsResp;

public class SoftwarePerformDiagnosticsCommand implements Command<PerformDiagnosticsReq, PerformDiagnosticsResp> {

	@Override
	public PerformDiagnosticsResp execute(PerformDiagnosticsReq req) {
		PerformDiagnosticsResp resp = new PerformDiagnosticsResp();
		resp.setFirmwareNumber("0000-1111");
		
		if (req.getLmkIdentifier() != null) {
			resp.setLmkCheck("1234567890123456");
		}
		
		resp.setErrorCode(ErrorsDef.NO_ERROR);
		
		return resp;
	}

}
