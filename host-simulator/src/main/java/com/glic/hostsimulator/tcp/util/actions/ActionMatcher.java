package com.glic.hostsimulator.tcp.util.actions;

import java.util.HashMap;
import java.util.Map;

import org.jpos.iso.ISOMsg;

public final class ActionMatcher {

   private static final int AMOUNT_ISO_FIELD = 4;

   private static final Map<String, IAction> ACTIONS_ABS = new HashMap<>();

   private static final IAction DEFAULT_ACTION = new DefaultAction();

   static {
     
   }

   private ActionMatcher() {
      // Hide constructor
   }

   public static IAction getAction(ISOMsg isoMsgRequest, String serverName) {
      switch (serverName) {
         default: {
            return DEFAULT_ACTION;
         }
      }
   }

}
