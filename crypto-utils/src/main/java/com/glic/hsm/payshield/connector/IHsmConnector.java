
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
