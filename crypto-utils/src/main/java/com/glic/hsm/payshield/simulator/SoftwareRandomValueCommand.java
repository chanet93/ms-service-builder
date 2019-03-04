package com.glic.hsm.payshield.simulator;

import org.apache.commons.lang3.RandomUtils;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.RandomValueReq;
import com.glic.hsm.payshield.response.RandomValueResp;

public class SoftwareRandomValueCommand implements Command<RandomValueReq, RandomValueResp> {

	@Override
	public RandomValueResp execute(RandomValueReq req) {
		
		RandomValueResp resp = new RandomValueResp();
		resp.setErrorCode(ErrorsDef.NO_ERROR);
		resp.setRandomBytes(RandomUtils.nextBytes(req.getSize()));
		
		return resp;
	}
}
