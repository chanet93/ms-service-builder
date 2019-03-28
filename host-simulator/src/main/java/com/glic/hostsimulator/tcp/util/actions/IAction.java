package com.glic.hostsimulator.tcp.util.actions;

import io.netty.channel.ChannelHandlerContext;

import org.jpos.iso.ISOMsg;

import com.glic.hostsimulator.tcp.ServerTcp;

public interface IAction {

   void apply(ISOMsg iso, ServerTcp serverTcp, ChannelHandlerContext ctx) throws Exception;

}
