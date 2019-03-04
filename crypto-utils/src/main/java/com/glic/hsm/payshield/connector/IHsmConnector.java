/*
 * Copyright (c) 2013. VeriFone Uruguay S.A. All Rights Reserved.
 * 
 * This Module contains Propietary Information of
 * VeriFone Uruguay S.A. and should be treated as Confidential.
 * 
 * This Module is provided "AS IS" Without any warranties implicit
 * or explicit.
 * 
 * The use of this Module implies the acceptance of all the terms
 * and conditions of the "User License".
 */
package com.glic.hsm.payshield.connector;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Interface for a HsmHandler
 * 
 * @date 3/10/2014
 * @version $Id$
 */
public interface IHsmConnector
{
    void connect() throws UnknownHostException, IOException;

    void closeConnection() throws IOException;
}
