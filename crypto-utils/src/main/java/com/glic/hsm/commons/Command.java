package com.glic.hsm.commons;

import com.glic.hsm.payshield.exception.HsmException;

@FunctionalInterface
public interface Command<REQ extends CommandReq, RESP extends CommandResp> {

	public RESP execute(REQ req) throws HsmException;

}
