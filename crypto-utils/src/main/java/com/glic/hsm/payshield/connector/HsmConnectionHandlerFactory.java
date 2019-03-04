package com.glic.hsm.payshield.connector;

public class HsmConnectionHandlerFactory {
	
	private int hsmPort;
	private String hsmHost;

	public HsmConnectionHandlerFactory(String hsmHost, int hsmPort) {
		this.hsmHost = hsmHost;
		this.hsmPort = hsmPort;
	}

	
	public HsmConnectionHandler createHSMConnectionHandler(){
		return new HsmConnectionHandler(getHsmHost(),getHsmPort());
	}
	
	public int getHsmPort() {
		return hsmPort;
	}

	public void setHsmPort(int hsmPort) {
		this.hsmPort = hsmPort;
	}

	public String getHsmHost() {
		return hsmHost;
	}

	public void setHsmHost(String hsmHost) {
		this.hsmHost = hsmHost;
	}

}
