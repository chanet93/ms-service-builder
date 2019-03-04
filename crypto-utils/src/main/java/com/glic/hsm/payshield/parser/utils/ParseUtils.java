package com.glic.hsm.payshield.parser.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 
 * @author Eetchart
 *
 */
public class ParseUtils {

	public static String getNextString(ByteBuffer responseByteBuffer,int bytesLenght,Charset charset) {
		byte[] readBytes = new byte[bytesLenght];
        responseByteBuffer.get(readBytes);
		return new String(readBytes, charset);
	}
	
	public static String getNextString(ByteBuffer responseByteBuffer,int bytesLenght,int offset,Charset charset) {
		byte[] readBytes = new byte[bytesLenght];
        responseByteBuffer.get(readBytes,offset,bytesLenght);
		return new String(readBytes, charset);
	}
	
	
	public static byte[] getNextBytes(ByteBuffer responseByteBuffer,int bytesLenght){
		byte[] bytes = new byte[bytesLenght];
		responseByteBuffer.get(bytes);
		return bytes;
	}
	
}