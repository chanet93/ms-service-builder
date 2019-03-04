package com.glic.hsm.payshield;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.HexDump;
import org.apache.commons.pool2.impl.GenericObjectPool;

import com.glic.hsm.payshield.connector.HsmConnectionHandler;
import com.glic.hsm.payshield.connector.HsmConnector;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.exception.HsmTimeoutException;

public class ThalesHsmConnector implements HsmConnector {

   private static final Logger LOGGER = Logger.getLogger(ThalesHsmConnector.class.getName());

   private String messageHeader;

   private String messageTrailer;

   private GenericObjectPool<HsmConnectionHandler> connectionPool;

   private int retries;

   @Override
   public ByteBuffer send(ByteBuffer payload) throws HsmException {
      HsmConnectionHandler connection = null;

      // TODO Use try-with-resources
      long startTime = System.currentTimeMillis();
      boolean error = false;
      try {
         LOGGER.log(Level.FINE, "Borrowing connection from pool");
         connection = connectionPool.borrowObject();

         long responseByteBufferTime = System.currentTimeMillis();
         ByteBuffer responseByteBuffer = getResponse(connection, render(payload));
         long responseByteBufferAllTime = System.currentTimeMillis() - responseByteBufferTime;
         LOGGER.log(Level.FINE, "ResponseByteBuffer: {0} ms.", responseByteBufferAllTime);
         return getResponsePayload(responseByteBuffer);

      } catch (SocketException se) {
         error = true;
         LOGGER.log(Level.WARNING, "Socket read timeout waiting for HSM response");
         throw new HsmTimeoutException("Socket read timeout waiting for HSM response", se);
      } catch (IOException se) {
         error = true;
         LOGGER.log(Level.WARNING, "IOException waiting for HSM response");
         throw new HsmTimeoutException("IOException waiting for HSM response", se);
      } catch (Exception e) {
         error = true;
         LOGGER.log(Level.WARNING, "Error processing HSM command", e);
         throw new HsmException("Error processing HSM command", e);
      } finally {
         if (!error) {
            connectionPool.returnObject(connection);
         } else {
            try {
               connectionPool.invalidateObject(connection);
            } catch (Exception e) {
               LOGGER.log(Level.WARNING, "Exception thrown removing bad HSM connection from pool");
            }

            try {
               connection.closeConnection();
            } catch (Exception e) {
               LOGGER.log(Level.WARNING, "Exception closing HSM connection");
            }
         }

         long elapsed = System.currentTimeMillis() - startTime;
         LOGGER.log(Level.FINE, "Elapsed HSM command time: {0} ms.", elapsed);
      }
   }

   @Override
   public void close() {
      LOGGER.log(Level.INFO, "Closing HSM connection pool");
      connectionPool.close();
   }

   // TODO moverlo a un renderer de todo el mensaje
   public ByteBuffer render(ByteBuffer payload) {
      int requestLength = messageHeader.length() + payload.limit() + messageTrailer.length();

      // 2-byte field for the message length
      ByteBuffer buffer = ByteBuffer.allocate(2 + requestLength);

      // message length represented in two unsigned bytes
      buffer.putShort((short) requestLength);

      buffer.put(messageHeader.getBytes(StandardCharsets.US_ASCII));
      buffer.put(payload.array());
      buffer.put(messageTrailer.getBytes(StandardCharsets.US_ASCII));

      return buffer;
   }

   // TODO moverlo a un parser de todo el mensaje
   public ByteBuffer getResponsePayload(ByteBuffer responseByteBuffer) {

      final int COMMAND_FIELD_SIZE = 2;
      final int MESSAGE_FIELD_SIZE = 2;

      responseByteBuffer.position(responseByteBuffer.position() + MESSAGE_FIELD_SIZE + messageHeader.length() + COMMAND_FIELD_SIZE);
      responseByteBuffer.limit(responseByteBuffer.limit() - messageTrailer.length());

      return responseByteBuffer.slice().asReadOnlyBuffer();
   }

   private ByteBuffer getResponse(HsmConnectionHandler connectionHandler, ByteBuffer requestBuffer) throws IOException {

      if (LOGGER.isLoggable(Level.FINER)) {
         ByteArrayOutputStream dumpStream = new ByteArrayOutputStream();
         HexDump.dump(requestBuffer.array(), 0, dumpStream, 0);

      }

      connectionHandler.getConnection().getOutputStream().write(requestBuffer.array());
      connectionHandler.getConnection().getOutputStream().flush();

      LOGGER.log(Level.FINE, "Sent {0} bytes to HSM. Waiting for HSM response", new Object[] { requestBuffer.limit() });

      byte[] responseLength = new byte[2];
      LOGGER.log(Level.FINER, "Waiting response length from HSM");
      InputStream in = connectionHandler.getConnection().getInputStream();
      int readBytes = in.read(responseLength);
      if (readBytes != 2) {
         throw new IOException("Invalid response length bytes. Expected=2, actual=" + readBytes);
      }

      // build respose byte[]
      short messageLength = fromByteArray(responseLength);
      LOGGER.log(Level.FINER, "Response length: {0} bytes", messageLength);
      byte[] response = new byte[2 + messageLength];
      System.arraycopy(responseLength, 0, response, 0, responseLength.length);

      int offset = 2;
      do {
         readBytes = in.read(response, offset, messageLength);
         offset += readBytes;
         messageLength -= readBytes;
      } while (readBytes > 0); // TODO agregar "|| messageLength == 0?

      LOGGER.log(Level.FINE, "Received {0} bytes from HSM", response.length);

      if (LOGGER.isLoggable(Level.FINER)) {
         ByteArrayOutputStream dumpStream = new ByteArrayOutputStream();
         HexDump.dump(response, 0, dumpStream, 0);

         LOGGER.log(Level.FINER, "Response message dump:\n{0}", dumpStream.toString(StandardCharsets.US_ASCII.name()));
      }

      return ByteBuffer.wrap(response);
   }

   // TODO pasar a Utils, testear
   private short fromByteArray(byte[] bytes) {
      ByteBuffer wrapped = ByteBuffer.wrap(bytes); // big-endian by default
      short num = wrapped.getShort();
      return num;
   }

   public String getMessageHeader() {
      return messageHeader;
   }

   public void setMessageHeader(String header) {
      this.messageHeader = header;
   }

   public String getMessageTrailer() {
      return messageTrailer;
   }

   public void setMessageTrailer(String trailer) {
      this.messageTrailer = trailer;
   }

   public void setConnectionPool(GenericObjectPool<HsmConnectionHandler> connectionPool) {
      this.connectionPool = connectionPool;
   }

   @Override
   public int getMaxRetries() {
      return getRetries();
   }

   public int getRetries() {
      return retries;
   }

   public void setRetries(int retries) {
      this.retries = retries;
   }

}
