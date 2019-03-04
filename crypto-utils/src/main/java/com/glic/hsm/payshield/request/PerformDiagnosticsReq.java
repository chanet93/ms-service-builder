package com.glic.hsm.payshield.request;


public class PerformDiagnosticsReq extends PayshieldCommandReq {

	public PerformDiagnosticsReq() {
		setCommandCode(PayshieldCommandReq.PERFORM_DIAGNOSTICS_CMD);
	}

}
