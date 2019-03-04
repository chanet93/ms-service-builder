package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.GenerateKeyReq;

public class GenerateKeyRequestRenderer implements RequestRenderer<GenerateKeyReq> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public ByteBuffer renderCommandRequest(GenerateKeyReq req) {
		
		int size = 7
			+ (req.getLmkIdentifier() != null ? 3 : 0);
		
		ByteBuffer command = ByteBuffer.allocate(size);
		
		command.put(req.getCommandCode().getBytes(StandardCharsets.US_ASCII));
		
		// mode
		command.put((byte)'0');
		
		// key type
		command.put("00B".getBytes(StandardCharsets.US_ASCII));
		
		// key scheme
		command.put((byte)'T');
		
		//variant key 
		//command.put((byte)'&');
		
		if (req.getLmkIdentifier() != null) {
			command.put("%".getBytes(ASCII));
			command.put(String.format("%02d", req.getLmkIdentifier()).getBytes(ASCII));
		}
		
		// TODO transform these parameters into GenerateSymmetricKeyReq fields?
		return command;
	}
}
