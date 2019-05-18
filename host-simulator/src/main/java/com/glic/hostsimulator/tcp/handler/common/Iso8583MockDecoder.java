package com.glic.hostsimulator.tcp.handler.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

import com.glic.hostsimulator.common.exception.MockServerException;

import lombok.extern.log4j.Log4j2;

@Sharable
@Log4j2
public class Iso8583MockDecoder extends MessageToMessageDecoder<ByteBuf> {

   private ISOPackager packager;

   public Iso8583MockDecoder(ISOPackager packager) {
      this.packager = packager;
   }

   @Override
   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
      try {
         final int length = msg.readableBytes();
         final byte[] array = new byte[length];
         msg.getBytes(msg.readerIndex(), array, 0, length);
         ISOMsg m = packager.createISOMsg();
         m.setPackager(packager);
         packager.unpack(m, array);
         out.add(m);
      } catch (Exception ex) {
         log.error("An error occur when decode ISO 8583 message.", ex);
         throw new MockServerException(ex);
      }
   }
}
