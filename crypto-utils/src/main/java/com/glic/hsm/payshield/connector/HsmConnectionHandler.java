package com.glic.hsm.payshield.connector;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

/**
 * Handles the connection to the HSM
 * 
 * @date 3/10/2014
 * @author ErnestoQ1
 */
public class HsmConnectionHandler implements IHsmConnector {
	
	private static final Logger LOGGER = Logger.getLogger(HsmConnectionHandler.class.getName());

	private static final int SOCKET_SO_TIMEOUT = 2000;

	private String ip;

	private int port;

	private Socket connection;

	private SSLContext sslCtx;

	private String fixedHeader;

	private boolean isSSL;

	/**
	 * Creates a connection with the HSM Thales 9000 using TCP/ip protocol
	 * 
	 * @param ip
	 * @param port
	 */
	public HsmConnectionHandler(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.isSSL = false;
	}

	/**
	 * Constructor for hsmhander with a ssl connection type
	 * 
	 * @param host
	 *            Host to connect
	 * @param port
	 *            The ip Port
	 * @param ctx
	 *            ssl context inited with loaded keys
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public HsmConnectionHandler(String host, int port, SSLContext ctx)
			throws UnknownHostException, IOException {
		this.ip = host;
		this.port = port;
		this.sslCtx = ctx;
		this.isSSL = true;
	}

	public HsmConnectionHandler() {
		this.isSSL = false;
	}

	/**
	 * Creates the connection with an HSM
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect() throws UnknownHostException, IOException {
		LOGGER.log(Level.FINE, "Connecting to HSM [host={0},port={1},ssl={2}]",	new Object[] { ip, port, isSSL });

		long startTime = System.currentTimeMillis();

		if (isSSL == false) {
			connection = new Socket(this.ip, this.port);
		} else {
			connection = sslCtx.getSocketFactory().createSocket(ip, port);
		}
		connection.setSoTimeout(SOCKET_SO_TIMEOUT);
		connection.setSoLinger(Boolean.TRUE, 0);//Avoid the TIME_WAIT
		connection.setKeepAlive(Boolean.TRUE);
		connection.setReuseAddress(Boolean.TRUE);

		long elapsed = System.currentTimeMillis() - startTime;

		LOGGER.log(Level.INFO, "Connected to HSM. Elapsed time: {0} ms.",elapsed);

	}

	/**
	 * Set the header, the header is a configuration of the HSM hardware,
	 * setting at install
	 * 
	 * @param header
	 */
	public void setHeader(String header) {
		this.setFixedHeader(header);
	}

	
	/**
	 * Close a previously initiated connection
	 * 
	 * @throws IOException
	 */
	public void closeConnection() {
		LOGGER.log(Level.FINE, "Closing connection to HSM");

		if (connection != null) {
			try {

				try {
					connection.getInputStream().close();
				} catch (Exception e) {
					LOGGER.log(Level.FINE, "Error closing input stream");
				}

				try {
					connection.getOutputStream().close();
				} catch (Exception e) {
					LOGGER.log(Level.FINE, "Error closing output stream");
				}
				
				try {
					connection.shutdownInput();
				} catch (Exception e) {
					LOGGER.log(Level.FINE, "Error shutdown input stream");
				}

				try {
					connection.shutdownOutput();
				} catch (Exception e) {
					LOGGER.log(Level.FINE, "Error shutdown output stream");
				}


			} finally {
				try {
					connection.close();
				} catch (Exception e) {
					LOGGER.log(Level.FINE, "Error closing connection");
				}
			}

		}

		LOGGER.log(Level.INFO, "Connection closed");
	}

	/**
	 * @return The socket instance
	 */
	public Socket getConnection() {
		return connection;
	}

	public String getFixedHeader() {
		return fixedHeader;
	}

	public void setFixedHeader(String fixedHeader) {
		this.fixedHeader = fixedHeader;
	}

	/**
	 * @return if the handler is connected
	 */
	public boolean isConnected() {
		return (connection != null && connection.isConnected());
	}

}