package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.DecryptRsaReq;
import com.glic.hsm.payshield.response.DecryptRsaResp;

public class DecryptRsaResponseParser extends AbstractBaseResponseParser<DecryptRsaReq, DecryptRsaResp> {

    private static final Charset CS = StandardCharsets.US_ASCII;

    @Override
    public DecryptRsaResp parseCommandResponse(ByteBuffer responseByteBuffer) {
        DecryptRsaResp resp = new DecryptRsaResp();

        byte[] errorCodeBytes = new byte[COMMAND_CODE_LENGTH];
        responseByteBuffer.get(errorCodeBytes);
        String errorCode = new String(errorCodeBytes, CS);
        resp.setErrorCode(errorCode);

        if (ErrorsDef.NO_ERROR.equals(errorCode)) {

            byte[] messageLengthBytes = new byte[4];
            responseByteBuffer.get(messageLengthBytes);

            String decodeLengthString = new String(messageLengthBytes, StandardCharsets.US_ASCII);
            int decodeLength = Integer.valueOf(decodeLengthString);

            byte[] paddedMessageBytes = new byte[decodeLength];
            responseByteBuffer.get(paddedMessageBytes);
            resp.setDecryptedData(paddedMessageBytes);
        }

        return resp;
    }
}
