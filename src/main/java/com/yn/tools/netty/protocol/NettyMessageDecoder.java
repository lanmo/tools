package com.yn.tools.netty.protocol;

import com.yn.tools.serialize.FastJsonSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by yangnan on 16/12/19.
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private FastJsonSerialization serialization = new FastJsonSerialization();

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null)
            return null;

        NettyMessage message = serialization.deserialize(in.array(), NettyMessage.class);
        return message;
    }
}
