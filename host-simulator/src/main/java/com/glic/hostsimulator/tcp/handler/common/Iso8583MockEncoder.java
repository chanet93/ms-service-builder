package com.glic.hostsimulator.tcp.handler.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

import com.glic.hostsimulator.common.exception.MockServerException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Sharable
public class Iso8583MockEncoder extends MessageToMessageEncoder<ISOMsg> {

   private ISOPackager packager;

   public Iso8583MockEncoder(ISOPackager packager) {
      this.packager = packager;
   }

   @Override
   protected void encode(ChannelHandlerContext ctx, ISOMsg msg, List<Object> out) throws Exception {
      try {
         msg.recalcBitMap();
         byte[] byteMsg = packager.pack(msg);
         ByteBuf frame = ctx.alloc().buffer(byteMsg.length);
         frame.writeBytes(byteMsg, 0, byteMsg.length);
         out.add(frame);
      } catch (ISOException ex) {
         log.error("An error occur when encode ISO 8583 message.", ex);
         throw new MockServerException(ex);
      }
   }
}
