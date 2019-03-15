package com.glic.hsm.nshield.request;

import java.util.ArrayList;
import java.util.List;

import com.glic.hsm.commons.CommandChainingItem;
import com.glic.hsm.commons.CommandReq;
import com.glic.hsm.commons.CommandResp;


public class CommandChainingReq extends NShieldCommandReq {

	private List<CommandChainingItem<? extends CommandReq, ? extends CommandResp>> commands;
	
	public CommandChainingReq() {
		commands = new ArrayList<>();
	}

	public List<CommandChainingItem<? extends CommandReq, ? extends CommandResp>> getCommands() {
		return commands;
	}

	public void setCommands(List<CommandChainingItem<? extends CommandReq, ? extends CommandResp>> commands) {
		this.commands = commands;
	}

	
}