package com.glic.hostsimulator.tcp.util.actions;

import io.netty.channel.ChannelHandlerContext;

import org.jpos.iso.ISOMsg;

import com.glic.hostsimulator.tcp.ServerTcp;
import com.glic.hostsimulator.tcp.StubbingTcp;
import com.glic.hostsimulator.tcp.handler.common.StubTcpHandler;

public class DefaultAction implements IAction {

   @Override
   public void apply(ISOMsg iso, ServerTcp serverTcp, ChannelHandlerContext ctx) throws Exception {
      ISOMsg response = StubbingTcp.getIsoMsgStubbedResponse(iso, serverTcp);
      ctx.writeAndFlush(response, ctx.channel().newPromise().addListener(new StubTcpHandler.HandleExceptionListener()));
   }
}
