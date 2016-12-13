package com.yn.tools.test;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yn.tools.monitor.Monitor;
import com.yn.tools.netty.msg.MsgPackSerialization;
import com.yn.tools.netty.msg.User;
import com.yn.tools.redis.RedisClient;
import com.yn.tools.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangnan on 16/12/12.
 */
public class ByteTest {
    public static void main(String[] args) throws JsonProcessingException {
        MsgPackSerialization serialization = new MsgPackSerialization();
        RedisClient.FastJsonSerialization fastJsonSerialization = new RedisClient.FastJsonSerialization();
        ObjectMapper mapper = new ObjectMapper();

        List<User> users = new ArrayList<User>();
        for (int i=0;i<1000000;i++) {
            User u = new User("i", i);
            users.add(u);
//            mapper.writeValueAsBytes(new User());
//            fastJsonSerialization.serialize(new User());
//            serialization.serialize(new User());
        }

        Monitor.begin();
        for (User user : users) {
            mapper.writeValueAsBytes(user);
        }
        Monitor.end("jackson");

        Monitor.begin();
        for (User user : users) {
            fastJsonSerialization.serialize(user);
        }
        Monitor.end("fastJsonSerialization");
        Monitor.begin();
        for (User u : users) {
            serialization.serialize(u);
        }
        Monitor.end("MsgPackSerialization");


    }
}
