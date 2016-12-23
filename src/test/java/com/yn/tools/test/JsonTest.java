package com.yn.tools.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yn.tools.monitor.Monitor;
import com.yn.tools.netty.msg.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

//        oneThread(users, num);
//        muThread(users, num);

        testParse(num);
    }

    public static void muThread(List<User> users, int num) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        ExecutorService executorService2 = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(num);
        CountDownLatch countDownLatch2 = new CountDownLatch(num);
        Monitor.begin();
        for (User u : users) {
            executorService.execute(new FastJsonThread(countDownLatch, u, null));
        }

        try {
            countDownLatch.await();
            Monitor.end("fastjson encode");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Monitor.begin();
        for (User u : users) {
            executorService2.execute(new JacksonThread(countDownLatch2, u, mapper, null));
        }

        try {
            countDownLatch2.await();
            Monitor.end("jackson encode");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        executorService2.shutdown();

    }

    public static void testParse(int num) {
        String str = "{\"userId\":379356,\"userName\":\"I 379356\"}";
        List<String> strs = new ArrayList<String>(num);
        for (int i=0; i<num; i++) {
            strs.add(new String(str));
        }

        ObjectMapper mapper = new ObjectMapper();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        ExecutorService executorService2 = Executors.newFixedThreadPool(1000);
        CountDownLatch countDownLatch = new CountDownLatch(num);
        CountDownLatch countDownLatch2 = new CountDownLatch(num);
        Monitor.begin();
        for (String s : strs) {
            executorService.execute(new FastJsonThread(countDownLatch, null, s));
        }

        try {
            countDownLatch.await();
            Monitor.end("fastjson parse");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Monitor.begin();
        for (String s : strs) {
            executorService2.execute(new JacksonThread(countDownLatch2, null, mapper, s));
        }

        try {
            countDownLatch2.await();
            Monitor.end("jackson parse");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        executorService2.shutdown();
    }

    public static void oneThread(List<User> users, int num) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
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

    static class FastJsonThread implements Runnable {

        private CountDownLatch countDownLatch;
        private User u;
        private String str;
        private FastJsonThread(CountDownLatch countDownLatch, User u, String str) {
            this.countDownLatch = countDownLatch;
            this.u = u;
            this.str = str;
        }

        public void run() {
            try {
//                JSON.toJSONString(u);
                JSON.parseObject(str, User.class);
            } catch (Exception e) {

            } finally {
                countDownLatch.countDown();
            }
        }
    }

    static class JacksonThread implements Runnable {

        private CountDownLatch countDownLatch;
        private User u;
        private ObjectMapper mapper;
        private String str;
        private JacksonThread(CountDownLatch countDownLatch, User u, ObjectMapper mapper, String str) {
            this.countDownLatch = countDownLatch;
            this.mapper = mapper;
            this.u = u;
            this.str = str;
        }

        public void run() {
            try {
//                this.mapper.writeValueAsString(u);
                mapper.readValue(str, User.class);
            } catch (Exception e) {

            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
