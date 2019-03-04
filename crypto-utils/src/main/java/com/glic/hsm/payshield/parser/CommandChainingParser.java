package com.glic.hsm.payshield.parser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.glic.hsm.commons.CommandChainingItem;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.ThalesCommand;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.CommandChainingReq;
import com.glic.hsm.payshield.request.PayshieldCommandReq;
import com.glic.hsm.payshield.response.CommandChainingResp;
import com.glic.hsm.payshield.response.HsmCommandResp;

public class CommandChainingParser extends AbstractBaseResponseParser<CommandChainingReq, CommandChainingResp> {

	private static final Charset ASCII = StandardCharsets.US_ASCII;
	
	@Override
	public CommandChainingResp parseCommandResponse(ByteBuffer responseByteBuffer) {
		
		CommandChainingResp resp = new CommandChainingResp();
		String errorCode = ParseUtils.getNextString(responseByteBuffer, COMMAND_CODE_LENGTH, ASCII);
		resp.setErrorCode(errorCode);
    	
    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {

    		byte[] commandsQtyBytes = ParseUtils.getNextBytes(responseByteBuffer, 2);
			int commandsQty = 10 * (commandsQtyBytes[0] - '0') + (commandsQtyBytes[1] - '0');
	
	    	for (int i = 0; i < commandsQty; i++) {
	    		// get command length
	    		byte[] commandLengthBytes = ParseUtils.getNextBytes(responseByteBuffer, 4); 

	    		int commandLength = 1000 * (commandLengthBytes[0] - '0') 
	    			+ 100 *(commandLengthBytes[1] - '0')
	    			+ 10 * (commandLengthBytes[2] - '0') 
	    			+ (commandLengthBytes[3] - '0');
	    		
	    		//THIS IS A FIX BECAUSE A BUG OF HSM, AFTER FIRMWARE UPDATE THIS MUST BE CHANGE
	    		ByteBuffer bufferAux = responseByteBuffer.asReadOnlyBuffer();
	    		String fullSubCommand  = ParseUtils.getNextString(bufferAux, commandLength,ASCII);
	    		int headerSize = Integer.parseInt(fullSubCommand.substring(fullSubCommand.length() -2));
	    		String header = fullSubCommand.substring(commandLength-2 - headerSize, commandLength-2);
	    		
	    		// parse sub command
	    		ByteBuffer commandByteBuffer = responseByteBuffer.slice();
	    		HsmCommandResp commandResp = parseSubcommand(commandByteBuffer,header,headerSize);
	        	resp.getCommandResponses().add(commandResp);
	        	
	        	// update response ByteBuffer position
	        	responseByteBuffer.position(responseByteBuffer.position() + commandLength);
			}
    	}
    	
    	return resp;
	}

	@SuppressWarnings("all")
	protected HsmCommandResp parseSubcommand(ByteBuffer commandByteBuffer,String headerAux,int headerSize) {
		
		HsmCommandResp result = null;
		List<CommandChainingItem<? extends PayshieldCommandReq, ? extends HsmCommandResp>> commands = getRequestMessage().getCommands();
		
		for (int i = 0; i < commands.size() ; i++) {
			CommandChainingItem<PayshieldCommandReq,HsmCommandResp> commandChainingItem = (CommandChainingItem<PayshieldCommandReq, HsmCommandResp>) commands.get(i);
			ThalesCommand command = (ThalesCommand) commandChainingItem.getCommand();
			ResponseParser responseParser = command.getResponseParser();
			if(headerAux.equals(commandChainingItem.getRequest().getHeader())){
				responseParser.setRequestMessage(commandChainingItem.getRequest());
				String commandCode = ParseUtils.getNextString(commandByteBuffer, COMMAND_CODE_LENGTH, ASCII);
				result = (HsmCommandResp) responseParser.parseCommandResponse(commandByteBuffer);
				commandByteBuffer.get();//delimiter
		        String header = ParseUtils.getNextString(commandByteBuffer, headerSize, ASCII);//header
		        result.setHeader(header);
		        String headerLength = ParseUtils.getNextString(commandByteBuffer, 2, ASCII);//header size
			}
		}
		return result;
	}

}