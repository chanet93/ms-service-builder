package com.glic.hsm.nshield.response;

import java.util.ArrayList;
import java.util.List;

import com.glic.hsm.commons.CommandResp;
import com.glic.hsm.nshield.request.CommandChainingReq;


public class CommandChainingResp extends CommandResp {

	private List<CommandResp> commandResponses;
	
	public CommandChainingResp() {
		commandResponses = new ArrayList<>();
	}

	public List<CommandResp> getCommandResponses() {
		return commandResponses;
	}
}
