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
package com.glic.hsm.payshield.connector.pool;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.glic.hsm.payshield.connector.HsmConnectionHandler;

/**
 * Factory for pool object usage
 *
 * @author ErnestoQ1
 * @date 23/10/2014
 */
public class HsmTcpConnectionFactory extends BasePooledObjectFactory<HsmConnectionHandler> {

   private static final String ECHO_MESSAGE = "00163030303042323030304348656c6c6f20776f726c6421";

   private static final Logger LOGGER = Logger.getLogger(HsmTcpConnectionFactory.class.getName());

   private String host;

   private int port;

   public HsmTcpConnectionFactory(String host, int port) {
      this.host = host;
      this.port = port;
   }

   /**
    * {@inheritDoc}
    *
    * @see BasePooledObjectFactory#create()
    */
   @Override
   public HsmConnectionHandler create() throws Exception {
      HsmConnectionHandler hsm = new HsmConnectionHandler(host, port);
      hsm.connect();
      return hsm;

   }

   @Override
   public PooledObject<HsmConnectionHandler> wrap(HsmConnectionHandler element) {
      return new DefaultPooledObject<>(element);
   }

   @Override
   public boolean validateObject(PooledObject<HsmConnectionHandler> element) {
      return sendKeepAlive(element.getObject());
   }

   @Override
   public void destroyObject(PooledObject<HsmConnectionHandler> element) throws Exception {
      super.destroyObject(element);
      HsmConnectionHandler hsm = element.getObject();
      hsm.closeConnection();
   }

   /**
    * @param hsm
    * @return if the Hsm response a request
    */
   private boolean sendKeepAlive(HsmConnectionHandler hsm) {

      long startTime = System.currentTimeMillis();

      String logMessage = "Echo message failed";
      Boolean result = Boolean.FALSE;

      final char[] echoMsg = ECHO_MESSAGE.toCharArray();

      try {

         byte[] echoBytes = Hex.decodeHex(echoMsg);
         hsm.getConnection().getOutputStream().write(echoBytes);
         hsm.getConnection().getOutputStream().flush();

         byte[] responseLength = new byte[2];
         InputStream in = hsm.getConnection().getInputStream();

         int readBytes = in.read(responseLength);

         ByteBuffer wrapped = ByteBuffer.wrap(responseLength); // big-endian by default
         short messageLength = wrapped.getShort();

         LOGGER.log(Level.FINE, "Echo test response length: {0} bytes", messageLength);

         byte[] response = new byte[2 + messageLength];
         System.arraycopy(responseLength, 0, response, 0, responseLength.length);

         int offset = 2;
         do {
            readBytes = in.read(response, offset, messageLength);
            offset += readBytes;
            messageLength -= readBytes;
         } while (readBytes > 0);

         LOGGER.log(Level.INFO, "Payshield Echo Response");

         if (readBytes != -1) {
            result = Boolean.TRUE;
         }

      } catch (Exception e) {
         LOGGER.log(Level.WARNING, "Error on Echo message");
      }

      long elapsed = System.currentTimeMillis() - startTime;

      if (result) {
         logMessage = "Echo message responsed elapsed time: {0} ms";
      }

      LOGGER.log(Level.FINE, logMessage, elapsed);

      return result;
   }

}
