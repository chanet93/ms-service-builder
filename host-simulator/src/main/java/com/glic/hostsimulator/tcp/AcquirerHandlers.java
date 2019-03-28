package com.glic.hostsimulator.tcp;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import com.glic.hostsimulator.tcp.handler.tcp.TcpExampleFrameDecoder;
import com.glic.hostsimulator.tcp.handler.tcp.TcpExampleFramePrepender;

public class AcquirerHandlers {

   static final Map<String, Handlers> ACQUIRER_HANDLERS = new HashMap<>();

   //Add to the HashMap acquirer specific encoder and decoder.
   static {
      ACQUIRER_HANDLERS.put("tcp-example", new Handlers(new TcpExampleFrameDecoder(), new TcpExampleFramePrepender()));
   }

   /**
    * Inner class to contain specific encoder and decoder handlers.
    */
   public static class Handlers {

      private MessageToMessageDecoder<ByteBuf> decoderHandler;

      private MessageToMessageEncoder<ByteBuf> encoderHandler;

      public Handlers(MessageToMessageDecoder<ByteBuf> decoderHandler, MessageToMessageEncoder<ByteBuf> encoderHandler) {
         this.decoderHandler = decoderHandler;
         this.encoderHandler = encoderHandler;
      }

      public MessageToMessageDecoder<ByteBuf> getDecoderHandler() {
         return decoderHandler;

      }

      public MessageToMessageEncoder<ByteBuf> getEncoderHandler() {
         return encoderHandler;
      }
   }

}
