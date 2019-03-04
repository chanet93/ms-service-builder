package com.glic.hsm.payshield.simulator;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomUtils;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.request.GenerateKeyReq;
import com.glic.hsm.payshield.response.GenerateKeyResp;

public class SoftwareGenerateKeyCommand implements Command<GenerateKeyReq, GenerateKeyResp> {

	@Override
	public GenerateKeyResp execute(GenerateKeyReq req) {
		
		GenerateKeyResp resp = new GenerateKeyResp();
		resp.setErrorCode(ErrorsDef.NO_ERROR);
		resp.setKey(RandomUtils.nextBytes(16));
		resp.setKeyCheckValue(Hex.encodeHexString(RandomUtils.nextBytes(3)));
		
		return resp;
	}
}
