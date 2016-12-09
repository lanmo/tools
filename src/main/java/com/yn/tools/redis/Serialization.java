package com.yn.tools.redis;

import com.alibaba.fastjson.TypeReference;

/**
 * Created by yangnan on 2016/12/9.
 */
public interface Serialization {
    /**
     * 序列化
     *
     * @param data
     * @return
     */
    byte[] serialize(Object data);

    /**
     * 反序列化
     *
     * @param data
     * @param clz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clz);

    /**
     * 反序列化
     *
     * @param data
     * @param tTypeReference
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, TypeReference<T> tTypeReference);
}
