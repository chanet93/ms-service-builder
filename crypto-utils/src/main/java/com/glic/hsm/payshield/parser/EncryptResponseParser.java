package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.EncryptReq;
import com.glic.hsm.payshield.response.EncryptResp;

public class EncryptResponseParser extends AbstractBaseResponseParser<EncryptReq, EncryptResp> {

	@Override
	public EncryptResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		EncryptResp resp = new EncryptResp();

		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		resp.setErrorCode(errorCode);

		if (ErrorsDef.NO_ERROR.equals(errorCode)) {
			
			if (!getRequestMessage().getEncryptionModeEnum().equals(EncryptionModeEnum.ECB)) {
				int ivLength = getRequestMessage().getInitializationVector().length * 2;
				byte[] outputIvBytes = ParseUtils.getNextBytes(responseByteBuffer, ivLength);
				resp.setOutputIv(outputIvBytes);
			}
			
			String messageLengthString = ParseUtils.getNextString(responseByteBuffer, 4, ASCII);
			
			int messageLength = Integer.parseInt(messageLengthString, 16);
			
			byte[] messageBytes = ParseUtils.getNextBytes(responseByteBuffer, messageLength);

			resp.setEncryptedData(messageBytes);
		}

		return resp;
	}
}
