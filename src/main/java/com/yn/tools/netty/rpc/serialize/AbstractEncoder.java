package com.yn.tools.netty.rpc.serialize;

import com.yn.tools.netty.rpc.exception.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yangnan on 2016/12/14.
 */
public abstract class AbstractEncoder extends MessageToByteEncoder implements Serialize  {

    protected Class<?> genericClass;

    public AbstractEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (genericClass.isInstance(o)) {
            byte[] data = encode(o);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }

    public Object decode(byte[] bytes) {
        throw new RpcException("unkonw decode");
    }
}
