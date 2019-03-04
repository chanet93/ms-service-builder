package com.glic.hsm.nshield;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.HsmStatsReq;
import com.glic.hsm.payshield.response.HsmStatsResp;

/**
 * @author erwine1
 */
public class StatCommand extends AbstractCommand implements Command<HsmStatsReq, HsmStatsResp> {

	public static final String SECURITY_WORLD = "SW-12.10.01";

	@Override
	public HsmStatsResp execute(HsmStatsReq req) {
		HsmStatsResp resp = new HsmStatsResp();
		resp.setFirmwareNumber(SECURITY_WORLD);
		resp.setErrorCode(ErrorsDef.NO_ERROR);
		return resp;
	}

}