package com.glic.hostsimulator.tcp.util.actions;

import org.jpos.iso.ISOMsg;

public final class ActionMatcher {

   private static final IAction DEFAULT_ACTION = new DefaultAction();

   static {

   }

   private ActionMatcher() {
      // Hide constructor
   }

   public static IAction getAction(ISOMsg isoMsgRequest, String serverName) {
      return DEFAULT_ACTION;
   }

}
