package com.glic.hsm.payshield.request;


public class HsmStatsReq extends PayshieldCommandReq {

	public HsmStatsReq() {
		setCommandCode(PayshieldCommandReq.HSM_STATUS_CMD);
	}

}
