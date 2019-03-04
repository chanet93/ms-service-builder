package com.glic.hsm;

import java.util.Objects;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.glic.hsm.nshield.NShieldHsmFactory;
import com.glic.hsm.payshield.ThalesCommandFactory;
import com.glic.hsm.payshield.ThalesHsmConnector;
import com.glic.hsm.payshield.ThalesUnpooledHsmConnector;
import com.glic.hsm.payshield.connector.HsmConnectionHandler;
import com.glic.hsm.payshield.connector.HsmConnectionHandlerFactory;
import com.glic.hsm.payshield.connector.pool.HsmTcpConnectionFactory;
import com.glic.hsm.payshield.simulator.SoftwareHsmFactory;

/**
 * Started in 2014 for someone, refactored in 2016
 * @date 12/12/2014
 * @version $Id$
 * @author erwine1
 */
public class HsmFactoryBuilder {

	private ConnectorType connectorType;

	private String messageHeader;

	private String messageTrailer;

	private GenericObjectPoolConfig poolConfig;

	private String hsmHost;

	private int hsmPort;
	
	private int maxRetries;
	
	public HsmFactoryBuilder() {
	}

	/**
	 *
	 * @return
	 */
	public HsmCommandFactory build() {
		switch (connectorType) {
			case THALES_PAYSHIELD_SIMULATED:
				return thalesPayshieldSimulatedFactory();
			case THALES_PAYSHIELD_POOLED:
				return thalesPayshieldPooledHsmFactory();
			case THALES_PAYSHIELD_UNPOOLED:
				return thalesUnpooledHsmFactory();
			case THALES_NSHIELD_SIMULATED:
				return thalesnShieldSimulaedFactory();
			case THALES_NSHIELD:
				return thalesnShieldFactory();
			default:
				return null;
		}
	}

	/**
	 *
	 * @return
	 */
	private HsmCommandFactory thalesnShieldFactory() {
		NShieldHsmFactory nShieldHsmFactory = new NShieldHsmFactory();
		nShieldHsmFactory.setProvider(NShieldHsmFactory.NSHIELD_PROVIDER);
		return nShieldHsmFactory;
	}

	/**
	 *
	 * @return
	 */
	private HsmCommandFactory thalesnShieldSimulaedFactory() {
		NShieldHsmFactory nShieldHsmFactory = new NShieldHsmFactory();
		nShieldHsmFactory.setProvider(NShieldHsmFactory.SUN_PROVIDER);
		return nShieldHsmFactory;
	}

	/**
	 *
	 * @return
	 */
	private HsmCommandFactory thalesPayshieldPooledHsmFactory() {

		if (Objects.isNull(poolConfig)) {
			poolConfig = new GenericObjectPoolConfig();
		}

		if (Objects.isNull(messageHeader)) {
			messageHeader = "";
		}

		if (Objects.isNull(messageTrailer)) {
			messageTrailer = "";
		}

		ThalesCommandFactory commandFactory = new ThalesCommandFactory();

		ThalesHsmConnector hsmConnector = new ThalesHsmConnector();
		hsmConnector.setMessageHeader(messageHeader);
		hsmConnector.setMessageTrailer(messageTrailer);
		hsmConnector.setRetries(maxRetries);

		HsmTcpConnectionFactory connectionFactory = new HsmTcpConnectionFactory(hsmHost, hsmPort);
		GenericObjectPool<HsmConnectionHandler> pool = new GenericObjectPool<>(connectionFactory, poolConfig);
		hsmConnector.setConnectionPool(pool);

		commandFactory.setHsmConnector(hsmConnector);

		return commandFactory;
	}

	/**
	 *
	 * @return
	 */
	private SoftwareHsmFactory thalesPayshieldSimulatedFactory() {
		return new SoftwareHsmFactory();
	}

	/**
	 *
	 * @return
	 */
	private HsmCommandFactory thalesUnpooledHsmFactory() {

		if (Objects.isNull(messageHeader)) {
			messageHeader = "";
		}

		if (Objects.isNull(messageTrailer)) {
			messageTrailer = "";
		}

		ThalesCommandFactory commandFactory = new ThalesCommandFactory();
		
		HsmConnectionHandlerFactory hsmConnectionHandlerFactory =  new HsmConnectionHandlerFactory(hsmHost,hsmPort);

		ThalesUnpooledHsmConnector hsmConnector = new ThalesUnpooledHsmConnector();
		hsmConnector.setConnection(hsmConnectionHandlerFactory.createHSMConnectionHandler());
		
		hsmConnector.setMessageHeader(messageHeader);
		hsmConnector.setMessageTrailer(messageTrailer);
		hsmConnector.setRetries(maxRetries);
		
		commandFactory.setHsmConnector(hsmConnector);

		return commandFactory;
	}


	public HsmFactoryBuilder setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
		return this;
	}


	public HsmFactoryBuilder messageHeader(String messageHeader) {
		this.messageHeader = messageHeader;
		return this;
	}

	public HsmFactoryBuilder connectorType(ConnectorType type) {
		this.connectorType = type;
		return this;
	}

	public HsmFactoryBuilder messageTrailer(String messageTrailer) {
		this.messageTrailer = messageTrailer;
		return this;
	}

	public HsmFactoryBuilder poolConfig(GenericObjectPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
		return this;
	}

	public HsmFactoryBuilder hsmHost(String hsmHost) {
		this.hsmHost = hsmHost;
		return this;
	}

	public HsmFactoryBuilder hsmPort(int hsmPort) {
		this.hsmPort = hsmPort;
		return this;
	}

}
