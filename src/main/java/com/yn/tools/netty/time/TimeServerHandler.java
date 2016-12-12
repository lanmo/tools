package com.yn.tools.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yangnan on 16/12/1.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    private int countter;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is " + ++countter + "times receive client:[ " + body + "]");
        body += "$_";
        ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}