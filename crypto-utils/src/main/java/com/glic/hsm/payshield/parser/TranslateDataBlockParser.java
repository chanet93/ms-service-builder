package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.TranslateDataBlockReq;
import com.glic.hsm.payshield.response.TranslateDataBlockResp;

public class TranslateDataBlockParser extends AbstractBaseResponseParser<TranslateDataBlockReq, TranslateDataBlockResp> {

	@Override
	public TranslateDataBlockResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		TranslateDataBlockResp resp = new TranslateDataBlockResp();
		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		resp.setErrorCode(errorCode);

		if (ErrorsDef.NO_ERROR.equals(errorCode)) {
			
			if (!getRequestMessage().getSourceEncryptionModeEnum().equals(EncryptionModeEnum.ECB)) {
				int ivLength = getRequestMessage().getSourceIv().length * 2;
				byte[] outputIvBytes = ParseUtils.getNextBytes(responseByteBuffer, ivLength);
				resp.setSourceIv(outputIvBytes);
			}
			
			if (!getRequestMessage().getDestinationEncryptionModeEnum().equals(EncryptionModeEnum.ECB)) {
				int ivLength = getRequestMessage().getDestinationIv().length * 2;
				byte[] outputIvBytes = ParseUtils.getNextBytes(responseByteBuffer, ivLength); 
				resp.setDestinationIv(outputIvBytes);
			}
			
			byte[] messageLengthBytes = ParseUtils.getNextBytes(responseByteBuffer, 4);
			int messageLength = Integer.parseInt(new String(messageLengthBytes, ASCII), 16);
			
			byte[] messageBytes = new byte[messageLength];
			
			responseByteBuffer.get(messageBytes);
			resp.setEncryptedData(messageBytes);
		}

		return resp;
	}
}
