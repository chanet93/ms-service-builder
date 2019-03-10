
package com.glic.hsm.payshield.response;


/**
 * @date 13/10/2014
 * @version $Id$
 */
public class EchoResp extends HsmCommandResp {
	private String messageData;

	public EchoResp() {
		setCommandCode(HsmCommandResp.ECHO_RESP);
	}
	
	public String getMessageData() {
		return messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}

}
