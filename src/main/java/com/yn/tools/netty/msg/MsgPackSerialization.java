package com.yn.tools.netty.msg;

import com.alibaba.fastjson.TypeReference;
import com.yn.tools.redis.Serialization;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * Created by yangnan on 16/12/12.
 */
public class MsgPackSerialization implements Serialization {

    public byte[] serialize(Object data) {
        try {
            MessagePack messagePack = new MessagePack();
            return messagePack.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] data, Class<T> clz) {
        MessagePack messagePack = new MessagePack();
        try {
            return messagePack.read(data, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] data, TypeReference<T> tTypeReference) {
        return null;
    }
}