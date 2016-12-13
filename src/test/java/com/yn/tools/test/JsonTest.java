package com.yn.tools.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yn.tools.monitor.Monitor;
import com.yn.tools.netty.msg.User;
import com.yn.tools.utils.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangnan on 16/12/13.
 */
public class JsonTest {

    public static void main(String[] args) throws IOException {
        List<User> users = new ArrayList<User>();
        int num = 10000000;
        for (int i=0; i<num; i++) {
            User user = new User();
            user.setUserId(i);
            user.setUserName("I " + i);
            users.add(user);

        }

        ObjectMapper mapper = new ObjectMapper();
        for (int i=0;i <100000;i++) {
            JSON.toJSONString(new User());
            mapper.writeValueAsString(new User());

        }

        Monitor.begin();
        for (User u : users) {
            String str = mapper.writeValueAsString(u);
        }
        Monitor.end("jackjson");

        Monitor.begin();
        for (User u : users) {
            JSON.toJSONString(u);
        }
        Monitor.end("fastjson");

        String str = "{\"userId\":379356,\"userName\":\"I 379356\"}";
        List<String> strs = new ArrayList<String>(num);
        for (int i=0; i<num; i++) {
            strs.add(new String(str));
        }

        Monitor.begin();
        for (String s : strs) {
           User u = mapper.readValue(s, User.class);
        }
        Monitor.end("jackson parse");

        Monitor.begin();
        for (String s : strs) {
            User u = JSON.parseObject(s, User.class);
        }
        Monitor.end("fastjson parse");
    }
}
