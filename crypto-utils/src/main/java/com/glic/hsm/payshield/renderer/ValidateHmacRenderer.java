package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.HashAlgorithm;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.exception.InvalidHashAlgorithmException;
import com.glic.hsm.payshield.request.ValidateHmacReq;

public class ValidateHmacRenderer implements RequestRenderer<ValidateHmacReq> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public ByteBuffer renderCommandRequest(ValidateHmacReq req) throws HsmException {
		
		HashAlgorithm hashAlgorithm = req.getHashAlgorithm();
		
		if (hashAlgorithm == null || hashAlgorithm == HashAlgorithm.MD5 || hashAlgorithm == HashAlgorithm.ISO101182) {
			String errorStr = String.format("Invalid hash algorithm [%s]", hashAlgorithm);
			throw new InvalidHashAlgorithmException(errorStr);
		}
		
		int size = req.getCommandCode().length() // 2 bytes ('GM')
			+ 2		// Identifier of the hash algorithm used to hash the data
			+ 4		// HMAC length
			+ req.getHmac().length	// HMAC
			+ 2		// HMAC key format
			+ 4		// HMAC key length
			+ req.getHmacKey().length	// HMAC key
			+ 1		// delimiter (';')
			+ 5		// data length
			+ req.getData().length;		// data
			
		ByteBuffer command = ByteBuffer.allocate(size);
	
		command.put(req.getCommandCode().getBytes(ASCII));
		command.put(String.format("%02d", hashAlgorithm.getAlgorithmId()).getBytes(ASCII));
		command.put(String.format("%04d", req.getHmac().length).getBytes(ASCII));
		command.put(req.getHmac());
		command.put("00".getBytes(ASCII)); // Variant LMK
		command.put(String.format("%04d", req.getHmacKey().length).getBytes(ASCII));
		command.put(req.getHmacKey());
		command.put(";".getBytes(ASCII));
		command.put(String.format("%05d", req.getData().length).getBytes(ASCII));
		command.put(req.getData());

		return command;
	}
}
