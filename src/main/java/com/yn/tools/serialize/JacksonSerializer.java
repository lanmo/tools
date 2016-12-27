package com.yn.tools.serialize;

import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by yangnan on 2016/12/27.
 */
public class JacksonSerializer implements Serialization {

    private ObjectMapper objectMapper = new ObjectMapper();

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
        return null;
    }
}
