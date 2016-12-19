package com.yn.tools.netty.protocol;

import com.yn.tools.serialize.FastJsonSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by yangnan on 16/12/19.
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    private FastJsonSerialization serialization;

    public NettyMessageEncoder() {
        this.serialization = new FastJsonSerialization();
    }

    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> list) throws Exception {
        if (msg == null || msg.getHeader() == null)
            throw new Exception("The encode message is null");

        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeBytes(serialization.serialize(msg));
    }
}
