package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.glic.hsm.commons.CommandChainingItem;
import com.glic.hsm.payshield.ThalesCommand;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.request.CommandChainingReq;
import com.glic.hsm.payshield.request.PayshieldCommandReq;
import com.glic.hsm.payshield.response.HsmCommandResp;

public class CommandChainingRenderer implements RequestRenderer<CommandChainingReq> {

	
	private static final Logger LOGGER = Logger.getLogger(CommandChainingRenderer.class.getName());
	
	private static final String SEPARATOR_HEX = "19";
	private static final String _04D = "%04d";
	private static final String _02D = "%02d";
	private static final String HEADER_PRESENT_FLAG = "0";
	private static final int SEPARATOR_LENGTH = 1; //1 BYTE
	private static final int HEADER_LENGTH = 2;
	private static final int COMMAND_CODE_LENGTH = 2;
	private static final int MESSAGE_HEADER_FLAG_LENGTH = 1;
	private static final int NUMBER_OF_COMMAND_LENGTH = 2;
	

	
	
	private static final Charset ASCII = StandardCharsets.US_ASCII;

	
	
	@SuppressWarnings("all")
	@Override
	public ByteBuffer renderCommandRequest(CommandChainingReq req) throws HsmException {
			
		LOGGER.log(Level.FINE, "Rendering {0} commands", req.getCommands().size());

		printCommandCodes(req);
		
		Map<String,ByteBuffer> commandBuffers = new HashMap<String,ByteBuffer>();


		for (int i = 0; i < req.getCommands().size() ; i++) {
			CommandChainingItem<PayshieldCommandReq,HsmCommandResp> commandChainingItem = (CommandChainingItem<PayshieldCommandReq, HsmCommandResp>) req.getCommands().get(i);
            
            ThalesCommand command = (ThalesCommand)commandChainingItem.getCommand();
            PayshieldCommandReq commandReq = commandChainingItem.getRequest();
            
            ByteBuffer commandBuffer = command.getRequestRenderer().renderCommandRequest(commandReq);
            commandBuffers.put(commandReq.getHeader(),commandBuffer);
        }
		
        int commandsLength = getSubCommandsTotalLength(commandBuffers);
		
        int nkCommandLength =  getNkCommandLength(commandsLength);
        
		ByteBuffer command = ByteBuffer.allocate(nkCommandLength);
		
		command.put(req.getCommandCode().getBytes(ASCII));
		command.put(HEADER_PRESENT_FLAG.getBytes(ASCII));
		command.put(String.format(_02D, req.getCommands().size()).getBytes(ASCII));
		
		for (Map.Entry<String, ByteBuffer> entry : commandBuffers.entrySet()) {
			entry.getValue().flip();
			command.put(getSubCommandLength(entry));//LENGTH
			command.put(entry.getValue());//COMMAND CODE + SUB COMMAND
			command.put(getTrailerSeparator());//SEPARATOR FOR TRAILER FIELD
			command.put(entry.getKey().getBytes(ASCII));//HEADER
			command.put(String.format(_02D, entry.getKey().length()).getBytes(ASCII));//HEADER LENGTH
		}
		
		
		return command;
	}

	
	
	private byte[] getTrailerSeparator(){
		try{
			return Hex.decodeHex(SEPARATOR_HEX.toCharArray());
		} catch (DecoderException e) {
			LOGGER.log(Level.FINE, "Error decoding character {0}" , SEPARATOR_HEX);
		}
		return null;
	}

	
	
	
	private byte[] getSubCommandLength(Map.Entry<String, ByteBuffer> entry) {
		return String.format(_04D,entry.getValue().limit() 
				+ HEADER_LENGTH 
				+ SEPARATOR_LENGTH 
				+ entry.getKey().length()).getBytes(ASCII);
	}

	
	
	
	
	private int getNkCommandLength(int commandsLength) {
		return COMMAND_CODE_LENGTH 
				+ MESSAGE_HEADER_FLAG_LENGTH 
				+ NUMBER_OF_COMMAND_LENGTH 
				+ commandsLength;
	}

	
	
	
	private void printCommandCodes(CommandChainingReq req) {
		List<String> commandCodes = new ArrayList<>();
		for (int i = 0; i < req.getCommands().size() ; i++) {
			CommandChainingItem<PayshieldCommandReq,HsmCommandResp> commandChainingItem = (CommandChainingItem) req.getCommands().get(i);
			commandCodes.add(commandChainingItem.getRequest().getCommandCode());
		}
		LOGGER.log(Level.FINE, "Command codes: {0}", new Object[] { commandCodes });
	}

	
	
	
	private int getSubCommandsTotalLength(Map<String, ByteBuffer> commandBuffers) {
		int commandsLength = 0;
        for (Map.Entry<String, ByteBuffer> entry : commandBuffers.entrySet())
        {
            commandsLength += 
            		4 
            		+ entry.getValue().capacity() 
            		+ SEPARATOR_LENGTH  //delimiter
            		+ entry.getKey().length() //header (using trailer space)
            		+ HEADER_LENGTH ;//header size
        }
		return commandsLength;
	}
	
}