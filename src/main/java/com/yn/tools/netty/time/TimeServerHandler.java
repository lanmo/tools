package com.yn.tools.netty.time;

import com.yn.tools.utils.L;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangnan on 16/12/1.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        L.d("buf.length", buf.readableBytes());
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        L.d("The time server receive order: " + body);
        DateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        String currentTime = "query time order".equalsIgnoreCase(body) ? df.format(new Date()) : "bad orde";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}