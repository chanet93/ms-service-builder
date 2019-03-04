package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.EchoReq;

public class EchoRenderer implements RequestRenderer<EchoReq> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public ByteBuffer renderCommandRequest(EchoReq req) {

		int messageDataLength = req.getMessageData().length();

		int size = req.getCommandCode().length() // 2 bytes ('B2')
			+ 4 // length field
			+ messageDataLength;

		ByteBuffer command = ByteBuffer.allocate(size);

		command.put(req.getCommandCode().getBytes(ASCII));
		command.put(String.format("%04X", messageDataLength).getBytes(ASCII));
		command.put(req.getMessageData().getBytes(ASCII));

		return command;
	}
}
