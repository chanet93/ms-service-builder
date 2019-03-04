package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.HashAlgorithm;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.exception.InvalidHashAlgorithmException;
import com.glic.hsm.payshield.request.GenerateHmacReq;

public class GenerateHmacRenderer implements RequestRenderer<GenerateHmacReq> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public ByteBuffer renderCommandRequest(GenerateHmacReq req) throws HsmException {
		
		HashAlgorithm hashAlgorithm = req.getHashAlgorithm();
		
		if (hashAlgorithm == null || hashAlgorithm == HashAlgorithm.MD5 || hashAlgorithm == HashAlgorithm.ISO101182) {
			String errorStr = String.format("Invalid hash algorithm [%s]", hashAlgorithm);
			throw new InvalidHashAlgorithmException(errorStr);
		}
		
		int size = req.getCommandCode().length() // 2 bytes ('LQ')
			+ 2		// Identifier of the hash algorithm used to hash the data
			+ 4		// Length of the output HMAC
			+ 2		// HMAC key format
			+ 4		// HMAC key length
			+ req.getHmacKey().length	// HMAC key
			+ 1		// delimiter (';')
			+ 5		// data length
			+ req.getData().length		// data
			+ (req.getLmkIdentifier() != null ? 3 : 0); // '%' + LMK identifier
		
		ByteBuffer command = ByteBuffer.allocate(size);
	
		command.put(req.getCommandCode().getBytes(ASCII));
		
		command.put(String.format("%02d", hashAlgorithm.getAlgorithmId()).getBytes(ASCII));
		
		int keyLength = hashAlgorithm.getHashBitsSize() / 8;
		command.put(String.format("%04d", keyLength).getBytes(ASCII));
		
		command.put("00".getBytes(ASCII)); // Variant LMK
		
		command.put(String.format("%04d", req.getHmacKey().length).getBytes(ASCII));
		command.put(req.getHmacKey());
		
		command.put(";".getBytes(ASCII));
		
		command.put(String.format("%05d", req.getData().length).getBytes(ASCII));
		command.put(req.getData());
		
		if (req.getLmkIdentifier() != null) {
			command.put("%".getBytes(ASCII));
			command.put(String.format("%02d", req.getLmkIdentifier()).getBytes(ASCII));
		}

		return command;
	}
}
