package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.request.TranslateDataBlockReq;

public class TranslateDataBlockRenderer implements RequestRenderer<TranslateDataBlockReq> {
	
	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public ByteBuffer renderCommandRequest(TranslateDataBlockReq req) throws HsmException {

		int size = req.getCommandCode().length() // 2 bytes ('M4')
			+ 2 	// Source Mode flag
			+ 2 	// Destination Mode flag
			+ 1 	// Input format flag
			+ 1 	// Output format flag
			+ 3 	// source key type
			+ 1 + req.getSourceKey().length // source key
			+ 3 	// destination key type
			+ 1 + req.getDestinationKey().length // destination key
			+ (req.getSourceEncryptionModeEnum().equals(EncryptionModeEnum.ECB) ? 0 : 2 * req.getSourceIv().length) 	// source IV
			+ (req.getDestinationEncryptionModeEnum().equals(EncryptionModeEnum.ECB) ? 0 : 2 * req.getDestinationIv().length) 	// destination IV
			+ 4		// message length
			+ req.getEncryptedData().length
			+ (req.getLmkIdentifier() != null ? 3 : 0); // '%' + LMK identifier
		
		ByteBuffer command = ByteBuffer.allocate(size);
		
		command.put(req.getCommandCode().getBytes(ASCII));

		// source mode flag
		command.put(req.getSourceEncryptionModeEnum().getCommandValue().getBytes(ASCII));

		// destination mode flag
		command.put(req.getDestinationEncryptionModeEnum().getCommandValue().getBytes(ASCII));

		// input mode flag
		command.put(req.getSourceFormat().getCommandValue().getBytes(ASCII));

		// output mode flag
		command.put(req.getDestinationFormat().getCommandValue().getBytes(ASCII));

		// source key type (DEK)
		command.put("00B".getBytes(ASCII));

		// source key
		command.put((byte)'T');
		command.put(req.getSourceKey());
		
		// Destination key type (DEK)
		command.put("00B".getBytes(ASCII));

		// Destination key
		command.put((byte)'T');
		command.put(req.getDestinationKey());

		// source initialization vector
		if (!req.getSourceEncryptionModeEnum().equals(EncryptionModeEnum.ECB)) {
			char[] ivHexChars = Hex.encodeHex(req.getSourceIv(), false);
			command.put(toBytes(ivHexChars));
		}

		// destination initialization vector
		if (!req.getDestinationEncryptionModeEnum().equals(EncryptionModeEnum.ECB)) {
			char[] ivHexChars = Hex.encodeHex(req.getDestinationIv(), false);
			command.put(toBytes(ivHexChars));
		}

		// message length
		command.put(String.format("%04X", req.getEncryptedData().length).getBytes(ASCII));
		
		// message
		command.put(req.getEncryptedData());

		if (req.getLmkIdentifier() != null) {
			command.put("%".getBytes(ASCII));
			command.put(String.format("%02d", req.getLmkIdentifier()).getBytes(ASCII));
		}
		
		return command;
	}
	
	private byte[] toBytes(char[] chars) {
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = ASCII.encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
		return bytes;
	}
}
