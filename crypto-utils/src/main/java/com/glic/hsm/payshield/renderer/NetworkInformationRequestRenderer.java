package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.request.NetworkInformationReq;

public class NetworkInformationRequestRenderer implements RequestRenderer<NetworkInformationReq> {

	private static final String NETWORK_INTERFACE = "H";
	
	@Override
	public ByteBuffer renderCommandRequest(NetworkInformationReq req) {
		
		int size = req.getCommandCode().length() 
				+ NETWORK_INTERFACE.length()
				+1; //1 byte; 
			
		ByteBuffer command = ByteBuffer.allocate(size);
		command.put(req.getCommandCode().getBytes(ASCII));
		command.put((NETWORK_INTERFACE).getBytes(ASCII));
		command.put(String.format("%01d", 1).getBytes(ASCII));
				
		return command;
	}
}
