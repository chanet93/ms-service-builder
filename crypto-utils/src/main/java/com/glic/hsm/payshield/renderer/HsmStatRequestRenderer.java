package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.HsmStatsReq;

public class HsmStatRequestRenderer implements RequestRenderer<HsmStatsReq> {

	private static final String _00 = "00";
	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public ByteBuffer renderCommandRequest(HsmStatsReq req) {
		
		int size = req.getCommandCode().length() + _00.length(); // '00' : Return status information
			
		ByteBuffer command = ByteBuffer.allocate(size);
		Charset cs = StandardCharsets.US_ASCII;
		command.put(req.getCommandCode().getBytes(cs));
		command.put(_00.getBytes(ASCII));

		return command;
	}
}
