package com.yn.tools.netty.rpc.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by yangnan on 2016/12/14.
 */
public abstract class AnstractDecoder extends ByteToMessageDecoder implements Serialize {

    protected Class<?> generateClass;

    public AnstractDecoder(Class<?> generateClass) {
        this.generateClass = generateClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws
            Exception {

    }
}
