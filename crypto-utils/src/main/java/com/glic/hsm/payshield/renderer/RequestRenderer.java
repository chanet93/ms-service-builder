package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.glic.hsm.commons.CommandReq;
import com.glic.hsm.payshield.exception.HsmException;

@FunctionalInterface
public interface RequestRenderer<REQ extends CommandReq> {

	public static final Charset ASCII = StandardCharsets.US_ASCII;
    ByteBuffer renderCommandRequest(REQ req) throws HsmException;
    
}