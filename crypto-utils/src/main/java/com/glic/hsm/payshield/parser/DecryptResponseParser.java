package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.DecryptReq;
import com.glic.hsm.payshield.response.DecryptResp;

public class DecryptResponseParser extends AbstractBaseResponseParser<DecryptReq, DecryptResp> {

	@Override
	public DecryptResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		DecryptResp resp = new DecryptResp();

		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		resp.setErrorCode(errorCode);

		if (ErrorsDef.NO_ERROR.equals(errorCode)) {
			
			if (!getRequestMessage().getEncryptionModeEnum().equals(EncryptionModeEnum.ECB)) {
				int ivLength = getRequestMessage().getInitializationVector().length * 2;
				byte[] outputIvBytes = ParseUtils.getNextBytes(responseByteBuffer, ivLength); 
				resp.setOutputIv(outputIvBytes);
			}
			
			String messageString = ParseUtils.getNextString(responseByteBuffer, 4, ASCII);
			
			int messageLength = Integer.parseInt(messageString, 16);
			
			byte[] paddedMessageBytes = ParseUtils.getNextBytes(responseByteBuffer, messageLength);
			resp.setDecryptedData(paddedMessageBytes);
		}

		return resp;
	}
}
