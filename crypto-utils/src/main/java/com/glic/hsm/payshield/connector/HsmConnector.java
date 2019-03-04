package com.glic.hsm.payshield.connector;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.exception.HsmException;

/**
 * This class represents a contract for thales HSM connector.
 */
public interface HsmConnector {

	ByteBuffer send(ByteBuffer payload) throws HsmException;

	void close();

	String getMessageTrailer();
	
	int getMaxRetries();
}
