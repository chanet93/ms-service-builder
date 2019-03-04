package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.HashAlgorithm;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.exception.InvalidHashAlgorithmException;
import com.glic.hsm.payshield.request.GenerateHmacKeyReq;

public class GenerateHmacKeyRenderer implements RequestRenderer<GenerateHmacKeyReq> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;

	@Override
	public ByteBuffer renderCommandRequest(GenerateHmacKeyReq req) throws HsmException {
		
		HashAlgorithm hashAlgorithm = req.getHashAlgorithm();
		
		if (hashAlgorithm == null || hashAlgorithm == HashAlgorithm.MD5 || hashAlgorithm == HashAlgorithm.ISO101182) {
			String errorStr = String.format("Invalid hash algorithm [%s]", hashAlgorithm);
			throw new InvalidHashAlgorithmException(errorStr);
		}
		
		int size = req.getCommandCode().length() // 2 bytes ('L0')
			+ 2 // Identifier of the hash algorithm used to hash the data
			+ 2 // HMAC key usage
			+ 4 // HMAC key length
			+ 2 // HMAC key format
			+ (req.getLmkIdentifier() != null ? 3 : 0); // '%' + LMK identifier

		ByteBuffer command = ByteBuffer.allocate(size);

		command.put(req.getCommandCode().getBytes(ASCII));
		
		command.put(String.format("%02d", req.getHashAlgorithm().getAlgorithmId()).getBytes(ASCII));
		
		command.put("03".getBytes(ASCII)); // HMAC generation and validation
		
		int keyLength = hashAlgorithm.getHashBitsSize() / 8;
		command.put(String.format("%04d", keyLength).getBytes(ASCII));
		
		command.put("00".getBytes(ASCII));
		
		if (req.getLmkIdentifier() != null) {
			command.put("%".getBytes(ASCII));
			command.put(String.format("%02d", req.getLmkIdentifier()).getBytes(ASCII));
		}

		return command;
	}
}
