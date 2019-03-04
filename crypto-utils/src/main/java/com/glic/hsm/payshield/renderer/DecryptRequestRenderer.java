package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import com.glic.hsm.payshield.enums.EncryptionModeEnum;
import com.glic.hsm.payshield.exception.HsmUnsupportedCommandException;
import com.glic.hsm.payshield.request.DecryptReq;

public class DecryptRequestRenderer implements RequestRenderer<DecryptReq> {

    private static final Charset ASCII = StandardCharsets.US_ASCII;

    @Override
    public ByteBuffer renderCommandRequest(DecryptReq req) throws HsmUnsupportedCommandException {

        if (!req.getEncryptionAlgorithmEnum().isImplementedToCallHSM()) {
            throw new HsmUnsupportedCommandException();
        }

        int size = req.getCommandCode().length() // 2 bytes ('M2')
                + 2 // Mode flag
                + 1 // Input format flag
                + 1 // Output format flag
                + 3 // key type
                + 1 + req.getKey().length // key
                + (req.getEncryptionModeEnum().equals(EncryptionModeEnum.ECB) ? 0 : 2 * req.getInitializationVector().length) // IV
                + 4 // message length
                + req.getEncryptedData().length
                + (req.getLmkIdentifier() != null ? 3 : 0); // '%' + LMK identifier

        ByteBuffer command = ByteBuffer.allocate(size);

        command.put(req.getCommandCode().getBytes(ASCII));

        // mode flag
        command.put(req.getEncryptionModeEnum().getCommandValue().getBytes(ASCII));

        // input mode flag
        command.put(req.getInputFlag().getCommandValue().getBytes(ASCII));

        // output mode flag
        command.put(req.getOutputFlag().getCommandValue().getBytes(ASCII));

        // key type (00B = DEK)
        command.put("00B".getBytes(ASCII));

        // key
        command.put((byte) 'T');
        command.put(req.getKey());

        // initialization vector
        if (!req.getEncryptionModeEnum().equals(EncryptionModeEnum.ECB)) {
            char[] ivHexChars = Hex.encodeHex(req.getInitializationVector(), false);
            command.put(toBytes(ivHexChars));
        }

        // message length
        command.put(String.format("%04X", req.getEncryptedData().length).getBytes(ASCII));

        // message
        command.put(req.getEncryptedData());

        // LMK id
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
