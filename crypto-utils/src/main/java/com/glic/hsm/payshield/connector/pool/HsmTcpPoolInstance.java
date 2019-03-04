package com.glic.hsm.payshield.connector.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.glic.hsm.ConnectorType;
import com.glic.hsm.HsmCommandFactory;
import com.glic.hsm.HsmFactoryBuilder;

public enum HsmTcpPoolInstance {
    
    INSTANCE;
    
    private static final String HSM_FIX_HEADER = "0000";
    private static HsmCommandFactory hsmFactory;
    private static HsmCommandFactory simulatedHsmFactory;
    
    private HsmTcpPoolInstance() {
        
    }
    
    public static void initHsmPool(String ip, int port, GenericObjectPoolConfig poolConfig, ConnectorType connectorType) {
        HsmFactoryBuilder builder = new HsmFactoryBuilder();
        hsmFactory = builder
                .connectorType(connectorType)
                .messageHeader(HSM_FIX_HEADER)
                .poolConfig(poolConfig)
                .hsmHost(ip)
                .hsmPort(port)
                .build();
    }
    
    public static void initHsmEmulator() {
        HsmFactoryBuilder builder = new HsmFactoryBuilder();
        simulatedHsmFactory = builder
                .connectorType(ConnectorType.THALES_PAYSHIELD_SIMULATED)
                .build();
    }
    
    public HsmCommandFactory getHsmFactory() {
        return hsmFactory;
    }
    
    public HsmCommandFactory getSimulatedHsmFactory() {
        return simulatedHsmFactory;
    }
    
    public void shutdown() {
        hsmFactory.shutdown();
    }
    
}
