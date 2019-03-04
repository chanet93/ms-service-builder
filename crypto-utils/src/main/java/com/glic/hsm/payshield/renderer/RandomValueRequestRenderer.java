package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.RandomValueReq;

public class RandomValueRequestRenderer implements RequestRenderer<RandomValueReq> {

	@Override
	public ByteBuffer renderCommandRequest(RandomValueReq req) {
		
		int size = req.getCommandCode().length() // 2 bytes ('N0')
			+ 3; //  Decimal number in the range ‘001’ to ‘256’

		ByteBuffer command = ByteBuffer.allocate(size);

		command.put(req.getCommandCode().getBytes(StandardCharsets.US_ASCII));
		command.put(String.format("%03d", req.getSize()).getBytes(StandardCharsets.US_ASCII));

		return command;
	}
}
