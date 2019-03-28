package com.glic.hostsimulator.common.xml.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlMarshallUtils {

   @SuppressWarnings("unchecked")
   public static <T> T unMarshallStringXmlObject(Class<T> tClass, File confFile) throws JAXBException {
      JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      return (T) jaxbUnmarshaller.unmarshal(confFile);
   }

}
