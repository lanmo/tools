package com.yn.tools.redis;

import com.alibaba.fastjson.TypeReference;
import com.yn.tools.serialize.FastJsonSerialization;
import com.yn.tools.serialize.Serialization;
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

    private static final int CONNECTION_TIMEOUT = 1000;

    private static final int MAX_REDIRECTIONS = 3;

    public RedisClient(List<Host> hosts, String password) {
        if (hosts == null) {
            throw new NullPointerException("hosts is null");
        }

        Set<HostAndPort> hostAndPorts = getJedisClusterNode(hosts);
        GenericObjectPoolConfig poolConfig = getDefaultPoolConfig();

        jc = new JedisCluster(hostAndPorts, CONNECTION_TIMEOUT, CONNECTION_TIMEOUT, MAX_REDIRECTIONS, password, poolConfig);
    }

    public RedisClient(List<Host> hosts, String password, int timeOut) {
        if (hosts == null) {
            throw new NullPointerException("redises is null");
        }

        Set<HostAndPort> hostAndPorts = getJedisClusterNode(hosts);
        GenericObjectPoolConfig poolConfig = getDefaultPoolConfig();

        jc = new JedisCluster(hostAndPorts, timeOut, timeOut, MAX_REDIRECTIONS, password, poolConfig);
    }

    protected Set<HostAndPort> getJedisClusterNode(List<Host> hosts) {
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        for (Host host : hosts) {
            hostAndPorts.add(new HostAndPort(host.getIp(), host.getPort()));
        }
        return  hostAndPorts;
    }

    protected  GenericObjectPoolConfig getDefaultPoolConfig() {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(DEFAULT_MAX_IDLE);
        poolConfig.setMaxTotal(DEFAULT_MAX_TOTAL);
        poolConfig.setMinIdle(DEFAULT_MIN_IDLE);

        return poolConfig;
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

}
