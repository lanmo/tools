package com.yn.tools.serialize;

import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

/**
 * Created by yangnan on 16/12/12.
 */
public class MsgPackSerialization implements Serialization {

    private ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    public byte[] serialize(Object data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] data, Class<T> clz) {
        try {
            return objectMapper.readValue(data, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deserialize(byte[] data, TypeReference<T> tTypeReference) {
        throw new RuntimeException("unsupported");
    }
}
