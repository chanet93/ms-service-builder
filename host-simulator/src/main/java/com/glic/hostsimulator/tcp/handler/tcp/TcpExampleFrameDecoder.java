package com.glic.hostsimulator.tcp.handler.tcp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

@ChannelHandler.Sharable
public class TcpExampleFrameDecoder extends MessageToMessageDecoder<ByteBuf> {

   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
      if (msg.readableBytes() > 1) {
         Short length = msg.getShort(0);
         if (msg.readableBytes() >= length) {
            msg.readBytes(2);
            out.add(msg.readBytes(length));
         }
      }
   }
}
