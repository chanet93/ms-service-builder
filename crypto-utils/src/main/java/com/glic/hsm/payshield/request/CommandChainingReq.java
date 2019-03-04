package com.glic.hsm.payshield.request;

import java.util.ArrayList;
import java.util.List;

import com.glic.hsm.commons.CommandChainingItem;
import com.glic.hsm.payshield.response.HsmCommandResp;

public class CommandChainingReq extends PayshieldCommandReq {

	private List<CommandChainingItem<? extends PayshieldCommandReq, ? extends HsmCommandResp>> commands;
	
	public CommandChainingReq() {
		setCommandCode(COMMAND_CHAINING_CMD);
		commands = new ArrayList<>();
	}

	public List<CommandChainingItem<? extends PayshieldCommandReq, ? extends HsmCommandResp>> getCommands() {
		return commands;
	}

	public void setCommands(List<CommandChainingItem<? extends PayshieldCommandReq, ? extends HsmCommandResp>> commands) {
		this.commands = commands;
	}

	
}