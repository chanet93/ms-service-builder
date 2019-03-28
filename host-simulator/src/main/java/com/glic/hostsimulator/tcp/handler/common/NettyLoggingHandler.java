package com.glic.hostsimulator.tcp.handler.common;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LoggingHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Log4j2
public class NettyLoggingHandler extends LoggingHandler {


   @Override
   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      logAction(format(ctx, "REGISTERED"));
      ctx.fireChannelRegistered();
   }

   @Override
   public void channelActive(ChannelHandlerContext ctx) throws Exception {
      logAction(format(ctx, "ACTIVE"));
      ctx.fireChannelActive();
   }

   @Override
   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      logAction(format(ctx, "INACTIVE"));
      ctx.fireChannelInactive();
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      logAction(format(ctx, "EXCEPTION", cause));
      ctx.fireExceptionCaught(cause);
   }

   @Override
   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      logAction(format(ctx, "USER_EVENT", evt));
      ctx.fireUserEventTriggered(evt);
   }

   @Override
   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      logAction(format(ctx, "BIND", localAddress));
      ctx.bind(localAddress, promise);
   }

   @Override
   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      logAction(format(ctx, "CONNECT", remoteAddress, localAddress));
      ctx.connect(remoteAddress, localAddress, promise);
   }

   @Override
   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      logAction(format(ctx, "DISCONNECT"));
      ctx.disconnect(promise);
   }

   @Override
   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      ctx.close(promise);
   }

   @Override
   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      logAction(format(ctx, "RECEIVED", msg));
      ctx.fireChannelRead(msg);
   }

   @Override
   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      logAction(format(ctx, "SENT", msg));
      ctx.write(msg, promise);
   }

   @Override
   public void flush(ChannelHandlerContext ctx) throws Exception {
      logAction(format(ctx, "FLUSH"));
      ctx.flush();
   }

   private static void logAction(String formattedMsg) {
      log.info(formattedMsg);
   }
}
