package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.PerformDiagnosticsReq;

public class PerformDiagnosticsRequestRenderer implements RequestRenderer<PerformDiagnosticsReq> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public ByteBuffer renderCommandRequest(PerformDiagnosticsReq req) {
	
		int size = req.getCommandCode().length()
				+ (req.getLmkIdentifier() != null ? 3 : 0); // '%' + LMK identifier
			
		ByteBuffer command = ByteBuffer.allocate(size);

		Charset cs = StandardCharsets.US_ASCII;
		
		command.put(req.getCommandCode().getBytes(cs));
		
		if (req.getLmkIdentifier() != null) {
			command.put("%".getBytes(ASCII));
			command.put(String.format("%02d", req.getLmkIdentifier()).getBytes(ASCII));
		}

		return command;
	}
}
