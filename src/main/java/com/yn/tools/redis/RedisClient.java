package com.yn.tools.redis;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yangnan on 2016/12/9.
 */
public class RedisClient {

    /*使用fastjson序列化对象*/
    private static Serialization serialization = new FastJsonSerialization();

    private JedisCluster jc = null;

    private static final int DEFAULT_MAX_TOTAL = 1024;

    private static final int DEFAULT_MAX_IDLE = 50;

    private static final int DEFAULT_MIN_IDLE = 10;

    private static final int CONNECTION_TIMEOUT = 3000;

    private static final int MAX_REDIRECTIONS = 6;

    public RedisClient(List<Redis> redises) {
        if (redises == null) {
            throw new NullPointerException("redises is null");
        }

        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        for (Redis redis : redises) {
            hostAndPorts.add(new HostAndPort(redis.getHost(), redis.getPort()));
        }

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(DEFAULT_MAX_IDLE);
        poolConfig.setMaxTotal(DEFAULT_MAX_TOTAL);
        poolConfig.setMinIdle(DEFAULT_MIN_IDLE);

        jc = new JedisCluster(hostAndPorts, CONNECTION_TIMEOUT, CONNECTION_TIMEOUT, MAX_REDIRECTIONS, poolConfig);
    }

    public String set(String key, Object value) {
        return jc.set(serialization.serialize(key), serialization.serialize(value));
    }

    public <T> T get(String key, Class<T> tClass) {
        return serialization.deserialize(jc.get(serialization.serialize(key)), tClass);
    }

    public <T> T get(String key) {
        return serialization.deserialize(jc.get(serialization.serialize(key)), new TypeReference<T>(){});
    }

    private static class FastJsonSerialization implements Serialization {

        public byte[] serialize(Object data) {
            SerializeWriter out = new SerializeWriter();
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.WriteEnumUsingToString, true);
            serializer.config(SerializerFeature.WriteClassName, true);
            serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
            serializer.write(data);

            return out.toBytes(null);
        }

        public <T> T deserialize(byte[] data, Class<T> clz) {
            return JSONObject.parseObject(data, clz);
        }

        public <T> T deserialize(byte[] data, TypeReference<T> tTypeReference) {
            return JSONObject.parseObject(new String(data), tTypeReference);
        }
    }
}
