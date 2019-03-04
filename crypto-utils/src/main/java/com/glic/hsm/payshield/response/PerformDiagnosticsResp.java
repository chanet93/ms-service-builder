package com.glic.hsm.payshield.response;


public class PerformDiagnosticsResp extends HsmCommandResp {

	private String lmkCheck;
	
	private String firmwareNumber;

	public PerformDiagnosticsResp() {
		setCommandCode(PERFORM_DIAGNOSTICS_RESP);
	}
	
	/**
	 * @return The LMK check value. This is the value returned by the Console V command.
	 */
	public String getLmkCheck() {
		return lmkCheck;
	}

	public void setLmkCheck(String lmkCheck) {
		this.lmkCheck = lmkCheck;
	}
	
	/**
	 * @return The firmware reference number in the form: xxxx-xxxx.
	 */
	public String getFirmwareNumber() {
		return firmwareNumber;
	}

	public void setFirmwareNumber(String firmwareNumber) {
		this.firmwareNumber = firmwareNumber;
	}

}
