package com.yn.tools.netty.msg;

import com.alibaba.fastjson.TypeReference;
import com.yn.tools.netty.rpc.exception.RpcException;
import com.yn.tools.redis.Serialization;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * Created by yangnan on 16/12/12.
 */
public class MsgPackSerialization implements Serialization {

    private MessagePack messagePack = new MessagePack();

    public byte[] serialize(Object data) {
        try {
            return messagePack.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] data, Class<T> clz) {
        try {
            return messagePack.read(data, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] data, TypeReference<T> tTypeReference) {
        throw new RpcException("unsupported");
    }
}
