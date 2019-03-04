package com.glic.hsm.commons;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @param <REQ>
 * @param <RESP>
 *
 *    This items represent a generic containter to any command for this handler.
 */
public class CommandChainingItem<REQ extends CommandReq, RESP extends CommandResp> implements Serializable {

	private static final long serialVersionUID = 6706698195525039639L;

	private REQ request;
	
	private Command<REQ, RESP> command;

	public CommandChainingItem() {
		super();
	}

	public CommandChainingItem(REQ request, Command<REQ, RESP> command) {
		super();
		this.request = request;
		this.command = command;
	}

	public REQ getRequest() {
		return request;
	}

	public void setRequest(REQ request) {
		this.request = request;
	}

	public Command<REQ, RESP> getCommand() {
		return command;
	}

	public void setCommand(Command<REQ, RESP> command) {
		this.command = command;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
