package com.glic.hsm.payshield.response;

import java.util.ArrayList;
import java.util.List;

public class CommandChainingResp extends HsmCommandResp {

	private List<HsmCommandResp> commandResponses;
	
	public CommandChainingResp() {
		setCommandCode(COMMAND_CHAINING_RESP);
		commandResponses = new ArrayList<>();
	}

	public List<HsmCommandResp> getCommandResponses() {
		return commandResponses;
	}
}
