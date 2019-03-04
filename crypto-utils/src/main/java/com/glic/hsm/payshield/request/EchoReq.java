package com.glic.hsm.payshield.request;



/**
 * Echo command request, used for ping a Hsm
 * 
 * @date 13/10/2014
 * @version $Id$
 */
public class EchoReq extends PayshieldCommandReq {
	
	private String messageData;

	/**
	 * @param messageData
	 *            Data to Echo
	 */
	public EchoReq(String messageData) {
		setCommandCode(PayshieldCommandReq.ECHO_CMD);
		this.messageData = messageData;
	}

	public String getMessageData()
	{
		return messageData;
	}

	public void setMessageData(String messageData)
	{
		this.messageData = messageData;
	}
}
