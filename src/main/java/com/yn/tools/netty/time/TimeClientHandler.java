package com.yn.tools.netty.time;

import com.yn.tools.utils.L;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yangnan on 16/12/1.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
//    private final ByteBuf firstMessage;
    private int counter;

    public TimeClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String req = "Hi, yangnan. Welcome to Netty.$_";
        for (int i = 0; i< 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(req.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("This is : " + ++counter + " times receive server:[" + msg +"]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
