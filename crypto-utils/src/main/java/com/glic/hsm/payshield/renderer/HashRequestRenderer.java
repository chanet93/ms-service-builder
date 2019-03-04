package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.HashReq;

public class HashRequestRenderer implements RequestRenderer<HashReq> {

	@Override
	public ByteBuffer renderCommandRequest(HashReq req) {
		
		int size = req.getCommandCode().length() // 2 bytes ('GM')
			+ 2		// Identifier of the hash algorithm used to hash the data
			+ 5		// Length of the message data to be hashed.
			+ req.getMessageData().length; // Data to be hashed.
			
		ByteBuffer command = ByteBuffer.allocate(size);

		Charset cs = StandardCharsets.US_ASCII;
		
		command.put(req.getCommandCode().getBytes(cs));
		command.put(String.format("%02d", req.getHashAlgorithm().getAlgorithmId()).getBytes(cs));
		command.put(String.format("%05d", req.getMessageData().length).getBytes(cs));
		command.put(req.getMessageData());

		return command;
	}
}
