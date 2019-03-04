package com.glic.hsm.payshield.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HsmStatsResp extends HsmCommandResp {

	private String ioBufferSize;

	private String ethernetType;

	private String tcpSockets;

	private String firmwareNumber;

	public HsmStatsResp() {
		setCommandCode(HSM_STATUS_RESP);
	}

	public String getIoBufferSize() {
		return ioBufferSize;
	}

	public void setIoBufferSize(String ioBufferSize) {
		this.ioBufferSize = ioBufferSize;
	}

	public String getEthernetType() {
		return ethernetType;
	}

	public void setEthernetType(String ethernetType) {
		this.ethernetType = ethernetType;
	}

	public String getTcpSockets() {
		return tcpSockets;
	}

	public void setTcpSockets(String tcpSockets) {
		this.tcpSockets = tcpSockets;
	}

	public String getFirmwareNumber() {
		return firmwareNumber;
	}

	public void setFirmwareNumber(String firmwareNumber) {
		this.firmwareNumber = firmwareNumber;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}