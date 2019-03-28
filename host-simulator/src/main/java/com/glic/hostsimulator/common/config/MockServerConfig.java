package com.glic.hostsimulator.common.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "MockServerConfig")
public class MockServerConfig {

   private List<MockServer> mockServers;

   @XmlElementWrapper(name = "MockServers", required = true)
   @XmlElement(name = "MockServer", required = true)
   public List<MockServer> getMockServers() {
      return mockServers;
   }

   public void setMockServers(List<MockServer> mockServers) {
      this.mockServers = mockServers;
   }
}
