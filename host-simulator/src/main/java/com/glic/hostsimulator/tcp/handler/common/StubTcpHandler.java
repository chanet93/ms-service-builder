package com.glic.hostsimulator.tcp.handler.common;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;

import org.jpos.iso.ISOMsg;

import com.glic.hostsimulator.tcp.ServerTcp;
import com.glic.hostsimulator.tcp.util.actions.ActionMatcher;
import com.glic.hostsimulator.tcp.util.actions.IAction;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.log4j.Log4j2;

@Sharable
@Log4j2
public class StubTcpHandler extends ChannelDuplexHandler {

   private static Executor executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("StubTcpHandler-%d").build());

   private ServerTcp serverTcp;

   public StubTcpHandler(ServerTcp serverTcp) {
      this.serverTcp = serverTcp;
   }

   @Override
   public void channelRead(ChannelHandlerContext ctx, Object oMsg) throws Exception {
      final ISOMsg isoMsgRequest = (ISOMsg) oMsg;
      IAction action = ActionMatcher.getAction(isoMsgRequest, serverTcp.getServerName());
      executor.execute(() -> {
         try {
            action.apply(isoMsgRequest, serverTcp, ctx);
         } catch (Exception ex) {
            log.error("Error processing TCP channel read", ex);
         }
      });
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      log.error("Error processing TCP Handler", cause);
   }

   public static void setExecutor(Executor executor) {
      StubTcpHandler.executor = executor;
   }

   public static class HandleExceptionListener implements ChannelFutureListener {

      @Override
      public void operationComplete(ChannelFuture future) {
         if (!future.isSuccess()) {
            log.error("Exception on outbound direction", future.cause());
            future.channel().close();
         }
      }
   }
}
