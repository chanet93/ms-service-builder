package com.glic.hsm.nshield.request;

/**
 * Echo command request, used for send enquiry command to HSM
 * 
 * @date 15/12/2016
 * @author erwine1
 */
public class EchoReq extends NShieldCommandReq {
	
	private String messageData;

	/**
	 * @param messageData
	 * Data to Echo
	 */
	public EchoReq(String messageData) {
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
