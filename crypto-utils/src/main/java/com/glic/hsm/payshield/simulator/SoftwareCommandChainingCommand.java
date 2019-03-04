package com.glic.hsm.payshield.simulator;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.glic.hsm.commons.Command;
import com.glic.hsm.commons.CommandChainingItem;
import com.glic.hsm.nshield.AbstractCommand;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.request.CommandChainingReq;
import com.glic.hsm.payshield.request.PayshieldCommandReq;
import com.glic.hsm.payshield.response.CommandChainingResp;
import com.glic.hsm.payshield.response.HsmCommandResp;

@SuppressWarnings("all")
public class SoftwareCommandChainingCommand extends AbstractCommand implements Command<CommandChainingReq, CommandChainingResp> {

	private static final Logger LOGGER = Logger.getLogger(SoftwareCommandChainingCommand.class.getName());
	
	@Override
	public CommandChainingResp execute(CommandChainingReq req) throws HsmException {
		
		CommandChainingResp resp = new CommandChainingResp();
		
		LOGGER.log(Level.FINE, "Executing {0} commands", req.getCommands().size());

		for (int i = 0; i < req.getCommands().size() ; i++) {

			CommandChainingItem item = req.getCommands().get(i);

			Command command = item.getCommand();
			PayshieldCommandReq commandReq = (PayshieldCommandReq) item.getRequest();
			HsmCommandResp commandResp = (HsmCommandResp) command.execute(commandReq);
			commandResp.setHeader(((PayshieldCommandReq)item.getRequest()).getHeader());
			resp.getCommandResponses().add(commandResp);
		}
		
		resp.setErrorCode(ErrorsDef.NO_ERROR);
		for (HsmCommandResp commandResp : resp.getCommandResponses()) {
			if (!commandResp.getErrorCode().equals(ErrorsDef.NO_ERROR)) {
				resp.setErrorCode(ErrorsDef.UNKNOWN_ERROR);
				break;
			}
		}
		
		return resp;
	}
}
