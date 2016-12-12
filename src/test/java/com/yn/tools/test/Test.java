package com.yn.tools.test;

import com.yn.tools.netty.msg.MsgPackSerialization;
import com.yn.tools.netty.msg.User;
import com.yn.tools.redis.RedisClient;
import com.yn.tools.utils.L;

/**
 * Created by yangnan on 16/12/12.
 */
public class Test {
    public static void main(String[] args) {
        MsgPackSerialization serialization = new MsgPackSerialization();
        RedisClient.FastJsonSerialization fastJsonSerialization = new RedisClient.FastJsonSerialization();

        for (int i=0;i<1000000;i++) {

        }

        long start = System.currentTimeMillis();
        for (int i=0; i<1000; i++) {
            User user = new User("i", i);
            serialization.serialize(user);
        }
        long end = System.currentTimeMillis();
        L.d("MsgPackSerialization spend", end - start);

        start = System.currentTimeMillis();
        for (int i=0; i<1000000; i++) {
            User user = new User("i", i);
            fastJsonSerialization.serialize(user);
        }
        end = System.currentTimeMillis();
        L.d("fastJsonSerialization spend", end - start);
    }
}
