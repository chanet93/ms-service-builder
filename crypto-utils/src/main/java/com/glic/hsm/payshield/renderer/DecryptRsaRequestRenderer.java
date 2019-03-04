package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.payshield.request.DecryptRsaReq;

public class DecryptRsaRequestRenderer implements RequestRenderer<DecryptRsaReq> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;

	@Override
	public ByteBuffer renderCommandRequest(DecryptRsaReq req) {
		
		int size =                      
                        + req.getCommandCode().length() // 2 bytes ('GI')
                        + 2 	// Mode flag
			+ 1 	// Input format flag
			+ 1 	// Output format flag
			+ 3 	// key type
                        + 1     //??
                        + 4     //private key length bytes
                        + req.getData().length // encrypted data
                        + req.getHsmKeyParameters().length()
                        + 4	// message length                         
			+ req.getPrivateKey().length // key
		;
                
		ByteBuffer command = ByteBuffer.allocate(size);
                
                command.put(req.getCommandCode().getBytes(ASCII));

                // encryption mode flag
		command.put(req.getEncryptMode().getCommandValue().getBytes(ASCII));
                
		// pad mode flag
		command.put(req.getMode().getCommandValue().getBytes(ASCII));
                
                // key type flag
		command.put(req.getKeyTypeEnum().getCommandValue().getBytes(ASCII));
                
                // message length
                command.put(String.format("%04d", req.getData().length).getBytes(ASCII));
		
		// message
		command.put(req.getData());
                
                // keyparameters
                command.put(req.getHsmKeyParameters().getBytes(ASCII));                
                
                // privatekey length
                 command.put(String.format("%04d", req.getPrivateKey().length).getBytes(ASCII));
                
                // privatekey
                command.put(req.getPrivateKey());         
                
		return command;
	}
	
}
