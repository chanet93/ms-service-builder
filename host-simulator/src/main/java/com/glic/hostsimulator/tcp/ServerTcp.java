package com.glic.hostsimulator.tcp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.util.ResourceUtils;

import com.glic.hostsimulator.common.config.MockServer;
import com.glic.hostsimulator.common.exception.MockServerException;
import com.glic.hostsimulator.tcp.model.TcpDefaultResponseDefinition;
import com.glic.hostsimulator.tcp.model.TcpMsgStubbing;

import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.org.apache.commons.lang3.StringUtils;


public class ServerTcp {

   private static final Logger LOG = LogManager.getLogger(ServerTcp.class);

   private static final String ISO_MAPPING_PATH = "isomapping/{}/iso93ascii.xml";

   private static final String MAPPINGS_PATH = "classpath:config/{}/mappings";

   private static final String DEFAULT_RESPONSE_MAPPING_PATH = "isomapping/{}/default-mapping.json";

   private static final ObjectMapper MAPPER = new ObjectMapper();

   private String serverName;

   private int port;

   private GenericPackager packager;

   private boolean ssl;

   private boolean withHeader = false;

   private int maxFrameLength;

   private int lengthFieldOffset;

   private int lengthFieldLength;

   private int lengthAdjustment;

   private int initialBytesToStrip;

   private MessageToMessageDecoder<ByteBuf> decoderHandler;

   private MessageToMessageEncoder<ByteBuf> encoderHandler;

   private Map<UUID, TcpMsgStubbing> stubs = new HashMap<>();

   private TcpDefaultResponseDefinition defaultResponseDefinition;

   private boolean isXml;

   public ServerTcp(MockServer mockServer) throws MockServerException, FileNotFoundException {
      this.isXml = StringUtils.equalsIgnoreCase(mockServer.getOutput(), "XML");
      this.serverName = mockServer.getServerName();
      this.port = mockServer.getPort();
      if (!this.isXml) {
         final String isoMappingPath = StringUtils.replace(ISO_MAPPING_PATH, "{}", serverName);

         try (InputStream is = ClassLoader.getSystemResourceAsStream(isoMappingPath)) {
            this.packager = new GenericPackager(is);
         } catch (IOException ex) {
            LOG.error("Unable to get iso definition file for server {}", serverName, ex);
            throw new MockServerException(ex);
         } catch (ISOException ex) {
            LOG.error("Unable to create packager for server {}", serverName, ex);
         }
      }

      this.ssl = mockServer.isSsl();

      withHeader = mockServer.isWithHeader();
      maxFrameLength = mockServer.getMaxFrameLength();
      lengthFieldOffset = mockServer.getLengthFieldOffset();
      lengthFieldLength = mockServer.getLengthFieldLength();
      lengthAdjustment = mockServer.getLengthAdjustment();
      initialBytesToStrip = mockServer.getInitialBytesToStrip();

      this.decoderHandler = AcquirerHandlers.ACQUIRER_HANDLERS.get(mockServer.getServerName()).getDecoderHandler();
      this.encoderHandler = AcquirerHandlers.ACQUIRER_HANDLERS.get(mockServer.getServerName()).getEncoderHandler();
      if (!this.isXml) {
         loadMapping();
         loadDefaultResponse();
      }
   }

   private void loadMapping() throws MockServerException, FileNotFoundException {

      final File[] files = filesFrom(this.serverName);
      if (Objects.isNull(files)) {
         LOG.error("No mapping files for server {}", this.serverName);
         throw new MockServerException("No mapping files");
      }

      for (File file : files) {
         try {
            addStub(MAPPER.readValue(file, TcpMsgStubbing.class));
         } catch (IOException ex) {
            LOG.error("Failed to deserialize mapping from file {}", file.getName(), ex);
            throw new MockServerException(ex);
         }
      }
   }

   private void loadDefaultResponse() throws MockServerException {
      String mappingPath = StringUtils.replace(DEFAULT_RESPONSE_MAPPING_PATH, "{}", serverName);
      try (InputStream file = ClassLoader.getSystemResourceAsStream(mappingPath)) {
         TcpDefaultResponseDefinition tcpDefaultResponseDefinition = MAPPER.readValue(file, TcpDefaultResponseDefinition.class);
         if (Objects.isNull(tcpDefaultResponseDefinition)) {
            LOG.error("No default response define for server {}", serverName);
            throw new MockServerException("No default response define");
         }
         this.defaultResponseDefinition = tcpDefaultResponseDefinition;
      } catch (IOException ex) {
         throw new MockServerException(ex);
      }
   }

   private void addStub(TcpMsgStubbing tcpMsgStubbing) {
      stubs.put(UUID.randomUUID(), tcpMsgStubbing);
   }

   public String getServerName() {
      return serverName;
   }

   public void setServerName(String serverName) {
      this.serverName = serverName;
   }

   public int getPort() {
      return port;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public GenericPackager getPackager() {
      return packager;
   }

   public void setPackager(GenericPackager packager) {
      this.packager = packager;
   }

   public boolean isSsl() {
      return ssl;
   }

   public void setSsl(boolean ssl) {
      this.ssl = ssl;
   }

   public boolean isWithHeader() {
      return withHeader;
   }

   public void setWithHeader(boolean withHeader) {
      this.withHeader = withHeader;
   }

   public int getMaxFrameLength() {
      return maxFrameLength;
   }

   public void setMaxFrameLength(int maxFrameLength) {
      this.maxFrameLength = maxFrameLength;
   }

   public int getLengthFieldOffset() {
      return lengthFieldOffset;
   }

   public void setLengthFieldOffset(int lengthFieldOffset) {
      this.lengthFieldOffset = lengthFieldOffset;
   }

   public int getLengthFieldLength() {
      return lengthFieldLength;
   }

   public void setLengthFieldLength(int lengthFieldLength) {
      this.lengthFieldLength = lengthFieldLength;
   }

   public int getLengthAdjustment() {
      return lengthAdjustment;
   }

   public void setLengthAdjustment(int lengthAdjustment) {
      this.lengthAdjustment = lengthAdjustment;
   }

   public int getInitialBytesToStrip() {
      return initialBytesToStrip;
   }

   public void setInitialBytesToStrip(int initialBytesToStrip) {
      this.initialBytesToStrip = initialBytesToStrip;
   }

   public MessageToMessageDecoder<ByteBuf> getDecoderHandler() {
      return decoderHandler;
   }

   public void setDecoderHandler(MessageToMessageDecoder<ByteBuf> decoderHandler) {
      this.decoderHandler = decoderHandler;
   }

   public MessageToMessageEncoder<ByteBuf> getEncoderHandler() {
      return encoderHandler;
   }

   public void setEncoderHandler(MessageToMessageEncoder<ByteBuf> encoderHandler) {
      this.encoderHandler = encoderHandler;
   }

   public Map<UUID, TcpMsgStubbing> getStubs() {
      return stubs;
   }

   public void setStubs(Map<UUID, TcpMsgStubbing> stubs) {
      this.stubs = stubs;
   }

   public TcpDefaultResponseDefinition getDefaultResponseDefinition() {
      return defaultResponseDefinition;
   }

   public void setDefaultResponseDefinition(TcpDefaultResponseDefinition defaultResponseDefinition) {
      this.defaultResponseDefinition = defaultResponseDefinition;
   }

   public boolean isXml() {
      return isXml;
   }

   public void setXml(boolean xml) {
      this.isXml = xml;
   }

   private static File[] filesFrom(String interfaceName) throws FileNotFoundException {
      String mappingPath = StringUtils.replace(MAPPINGS_PATH, "{}", interfaceName);
      File configDir = ResourceUtils.getFile(mappingPath);
      return configDir.listFiles();
   }
}
