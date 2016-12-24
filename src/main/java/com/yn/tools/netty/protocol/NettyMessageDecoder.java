package com.yn.tools.netty.protocol;

import com.google.common.collect.Maps;
import com.yn.tools.serialize.FastJsonSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import javax.sound.midi.Soundbank;
import java.util.Map;

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

//        NettyMessage message = new NettyMessage();
//        Header header = new Header();
//        header.setCrcCode(in.readInt());
//        header.setLength(in.readInt());
//        header.setSessionId(in.readLong());
//        header.setType(in.readByte());
//        header.setPriority(in.readByte());
//        int size = in.readInt();
//        if (size > 0) {
//            Map<String, Object> attch = Maps.newHashMapWithExpectedSize(size);
//            for (int i=0; i<size; i++) {
//                byte[] keyArray = new byte[in.readInt()];
//                in.readBytes(keyArray);
//                String key = new String(keyArray);
//                byte[] valueArray = new byte[in.readInt()];
//                in.readBytes(valueArray);
//                Object value = serialization.deserialize(valueArray, Object.class);
//                attch.put(key, value);
//            }
//
//            header.setAttachment(attch);
//        }
//
//        if (in.readableBytes() > 4) {
//            byte[] data = new byte[in.readInt()];
//            in.readBytes(data);
//            message.setBody(serialization.deserialize(data, Object.class));
//        }
//
//        message.setHeader(header);

        System.out.println(frame.readableBytes());
        int length = frame.readInt();
        byte[] data = new byte[length];
        frame.readBytes(data);
        NettyMessage message = serialization.deserialize(data, NettyMessage.class);
        System.out.println(message);
        return message;
    }
}
