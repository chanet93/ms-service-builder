package com.glic.hostsimulator.tcp.util;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import com.glic.hostsimulator.tcp.ServerTcp;

public class IsoLogger {

   public static String isoFormatterLog(ISOMsg msg) throws ISOException {

      StringBuilder isoMsg = new StringBuilder();

      isoMsg.append("(+)[").append(msg.getMTI()).append("]\n");
      if (Objects.nonNull(msg.getISOHeader())) {
         isoMsg.append("  [HD] = ").append(ISOUtil.hexString(msg.getHeader())).append('\n');
      }

      for (int i = 1; i <= msg.getMaxField(); ++i) {
         if (msg.hasField(i)) {
            isoMsg.append("  [").append(i).append("] = ");
            isoMsg.append("\'").append(msg.getString(i)).append("\'\n");
         }
      }
      return isoMsg.toString();
   }

   public static String getLogPrefix(ServerTcp serverTcp) {
      return StringUtils.replace("[Server: {}] - ", "{}", serverTcp.getServerName());
   }
}
