package com.yn.tools.netty.protocol;

import com.yn.tools.serialize.FastJsonSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Map;

/**
 * Created by yangnan on 16/12/19.
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    private FastJsonSerialization serialization;

    public NettyMessageEncoder() {
        this.serialization = new FastJsonSerialization();
    }

    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
        if (msg == null || msg.getHeader() == null)
            throw new Exception("The encode message is null");

        if (msg == null || msg.getHeader() == null) {
            throw new IllegalArgumentException("The encode message is null");
        }

//        ByteBuf sendBuf =  byteBuf;

        Header header = msg.getHeader();
        sendBuf.writeInt(header.getCrcCode());
        sendBuf.writeInt(header.getLength());
        sendBuf.writeLong(header.getSessionId());
        sendBuf.writeByte(header.getType());
        sendBuf.writeByte(header.getPriority());

        //getAttachment 编码
        if (header.getAttachment() != null && !header.getAttachment().isEmpty()) {
            sendBuf.writeInt(header.getAttachment().size());
            for (Map.Entry<String, Object> entry : header.getAttachment().entrySet()) {
                sendBuf.writeInt(entry.getKey().length());
                sendBuf.writeBytes(entry.getKey().getBytes());
                byte[] body = serialization.serialize(entry.getValue());
                sendBuf.writeInt(body.length);
                sendBuf.writeBytes(body);
            }
        }

        //body 编码
        if (msg.getBody() != null) {
            byte[] body = serialization.serialize(msg.getBody());
            sendBuf.writeInt(body.length);
            sendBuf.writeBytes(body);
        }
//        else {
//            sendBuf.writeInt(0);
//            sendBuf.setInt(4, sendBuf.readableBytes());
//        }
//        byte[] data = serialization.serialize(msg);
//        sendBuf.writeInt(data.length);
//        sendBuf.writeBytes(data);
//        System.out.println(data.length);
    }
}
