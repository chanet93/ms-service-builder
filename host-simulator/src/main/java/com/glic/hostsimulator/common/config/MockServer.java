package com.glic.hostsimulator.common.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.glic.hostsimulator.common.exception.MockServerException;

@XmlRootElement(name = "MockServer")
public class MockServer {

   private String serverName;

   private int port;

   private ServerTypeEnum serverType;

   private boolean ssl;

   private boolean active = true;

   private boolean withHeader = false;

   private int maxFrameLength;

   private int lengthFieldOffset;

   private int lengthFieldLength;

   private int lengthAdjustment;

   private int initialBytesToStrip;

   private String output;

   @XmlElement(name = "ServerName", required = true)
   public String getServerName() {
      return serverName;
   }

   public void setServerName(String serverName) {
      this.serverName = serverName;
   }

   @XmlElement(name = "Port", required = true)
   public int getPort() {
      return port;
   }

   public void setPort(int port) {
      this.port = port;
   }

   @XmlElement(name = "ServerType", required = true)
   public ServerTypeEnum getServerType() {
      return serverType;
   }

   public void setServerType(ServerTypeEnum serverType) {
      this.serverType = serverType;
   }

   @XmlElement(name = "Ssl", required = true)
   public boolean isSsl() {
      return ssl;
   }

   public void setSsl(boolean ssl) {
      this.ssl = ssl;
   }

   @XmlElement(name = "Active", required = true)
   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   @XmlElement(name = "Header", defaultValue = "false", nillable = true)
   public boolean isWithHeader() {
      return withHeader;
   }

   public void setWithHeader(boolean withHeader) {
      this.withHeader = withHeader;
   }

   @XmlElement(name = "MaxFrameLength", nillable = true)
   public int getMaxFrameLength() {
      return maxFrameLength;
   }

   public void setMaxFrameLength(int maxFrameLength) {
      this.maxFrameLength = maxFrameLength;
   }

   @XmlElement(name = "LengthFieldOffset", nillable = true)
   public int getLengthFieldOffset() {
      return lengthFieldOffset;
   }

   public void setLengthFieldOffset(int lengthFieldOffset) {
      this.lengthFieldOffset = lengthFieldOffset;
   }

   @XmlElement(name = "LengthFieldLength", nillable = true)
   public int getLengthFieldLength() {
      return lengthFieldLength;
   }

   public void setLengthFieldLength(int lengthFieldLength) {
      this.lengthFieldLength = lengthFieldLength;
   }

   @XmlElement(name = "LengthAdjustment", nillable = true)
   public int getLengthAdjustment() {
      return lengthAdjustment;
   }

   public void setLengthAdjustment(int lengthAdjustment) {
      this.lengthAdjustment = lengthAdjustment;
   }

   @XmlElement(name = "InitialBytesToStrip", nillable = true)
   public int getInitialBytesToStrip() {
      return initialBytesToStrip;
   }

   public void setInitialBytesToStrip(int initialBytesToStrip) {
      this.initialBytesToStrip = initialBytesToStrip;
   }

   @XmlElement(name = "Output", nillable = true)
   public String getOutput() {
      return output;
   }

   public void setOutput(String output) {
      this.output = output;
   }

   public void validateConfig() throws MockServerException {
      if (this.serverName == null || this.serverName.equals("")) {
         throw new MockServerException("ServerName must not be empty or missing \"ServerName\" tag!.");
      }
      if (this.port <= 0) {
         throw new MockServerException("Invalid port specified or missing \"Port\" tag!.");
      }
      if (this.serverType == null) {
         throw new MockServerException("Invalid server type specified or missing \"ServerType\" tag, only possible values are \"HTTP\" or \"TCP\"");
      }
   }

   @Override
   public String toString() {
      return "Starting server...\nServer detail:\n\tName: " + serverName + "\n\tPort: " + port + "\n\tType: " + serverType + "\n\tSsl: " + ssl
            + "\n\tActive: " + active + "\n";
   }

}
