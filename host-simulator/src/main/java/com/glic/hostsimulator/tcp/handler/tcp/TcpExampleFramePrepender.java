package com.glic.hostsimulator.tcp.handler.tcp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class TcpExampleFramePrepender extends MessageToMessageEncoder<ByteBuf> {

   @Override
   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
      int msgLength = msg.readableBytes();
      ByteBuf buf = ctx.alloc().buffer(msgLength + 2);
      buf.writeShort(msgLength);
      buf.writeBytes(msg.retain());
      out.add(buf);
   }
}


