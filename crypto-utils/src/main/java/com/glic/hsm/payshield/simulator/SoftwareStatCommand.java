package com.glic.hsm.payshield.simulator;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.HsmStatsReq;
import com.glic.hsm.payshield.response.HsmStatsResp;

public class SoftwareStatCommand implements Command<HsmStatsReq, HsmStatsResp> {

	private static final String _0000_1111 = "0000-1111";

	@Override
	public HsmStatsResp execute(HsmStatsReq req) {
		HsmStatsResp resp = new HsmStatsResp();
		resp.setFirmwareNumber(_0000_1111);
		
		resp.setErrorCode(ErrorsDef.NO_ERROR);
		
		return resp;
	}

}