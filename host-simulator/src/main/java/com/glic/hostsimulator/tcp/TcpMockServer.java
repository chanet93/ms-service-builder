package com.glic.hostsimulator.tcp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.glic.hostsimulator.common.config.MockServer;
import com.glic.hostsimulator.common.exception.MockServerException;
import com.glic.hostsimulator.tcp.handler.common.Iso8583MockDecoder;
import com.glic.hostsimulator.tcp.handler.common.Iso8583MockEncoder;
import com.glic.hostsimulator.tcp.handler.common.NettyLoggingHandler;
import com.glic.hostsimulator.tcp.handler.common.StubTcpHandler;

public class TcpMockServer {

   public static final String SSL_SERVER_CRT_PATH = "config/ssl/server.crt.pem";

   public static final String SSL_SERVER_KEY_PATH = "config/ssl/server.key.pem";

   private static final Logger LOG = LogManager.getLogger(TcpMockServer.class);

   public void startServer(MockServer mockServer) throws MockServerException, FileNotFoundException {
      ServerTcp serverTcp = new ServerTcp(mockServer);
      startServer(serverTcp);
   }

   private static void startServer(ServerTcp serverTcp) {
      ServerBootstrap b = new ServerBootstrap();

      ChannelHandler channelInit = new ChannelInitializer<SocketChannel>() {

         @Override
         protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            if (serverTcp.isSsl()) {
               SslContext sslCtx = SslContextBuilder
                     .forServer(new File(SSL_SERVER_CRT_PATH), new File(SSL_SERVER_KEY_PATH), null)
                     .trustManager(InsecureTrustManagerFactory.INSTANCE)
                     .build();
               pipeline.addLast("ssl-handler", sslCtx.newHandler(ch.alloc()));
            }

            pipeline.addLast("logging-handler", new NettyLoggingHandler());

            if (serverTcp.isWithHeader()) {
               pipeline.addLast("length-decoder", new LengthFieldBasedFrameDecoder(serverTcp.getMaxFrameLength(), serverTcp.getLengthFieldOffset(),
                     serverTcp.getLengthFieldLength(), serverTcp.getLengthAdjustment(), serverTcp.getInitialBytesToStrip()));
            }

            ChannelHandler decoder = serverTcp.getDecoderHandler();
            if (Objects.nonNull(decoder)) {
               pipeline.addLast("frame-decoder", decoder);
            }

            ChannelHandler encoder = serverTcp.getEncoderHandler();
            if (Objects.nonNull(encoder)) {
               pipeline.addLast("frame-prepender", encoder);
            }
            if (!serverTcp.isXml()) {
               pipeline.addLast("iso-encoder", new Iso8583MockEncoder(serverTcp.getPackager()));
               pipeline.addLast("iso-decoder", new Iso8583MockDecoder(serverTcp.getPackager()));
               pipeline.addLast("stub-handler", new StubTcpHandler(serverTcp));
            } else {
               //pipeline.addLast("Xml-handler", new FirstDataStubTcpXmlHandler());
            }
         }
      };

      EventLoopGroup bossGroup = new NioEventLoopGroup();
      EventLoopGroup workerGroup = new NioEventLoopGroup();
      b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
      WriteBufferWaterMark writeBufOption = new WriteBufferWaterMark(2048, 131072);
      b
            .childHandler(channelInit)
            .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.SO_BACKLOG, 500)
            .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, writeBufOption)
            .option(ChannelOption.SO_RCVBUF, 2097152);

      try {
         final ChannelFuture ex = b.bind("127.0.0.1", serverTcp.getPort()).sync();
         ex.addListener((ChannelFutureListener) future -> {
            LOG.info("Interface server started: {}", serverTcp.getServerName());
            Runtime.getRuntime().addShutdownHook(new Thread() {

               @Override
               public void run() {
                  LOG.info("Interface stoped: {}", serverTcp.getServerName());
                  bossGroup.shutdownGracefully();
                  workerGroup.shutdownGracefully();
               }
            });
         });
      } catch (Exception ex) {
         LOG.error("Interface not initialized: {}", serverTcp.getServerName(), ex);
      }
   }
}
