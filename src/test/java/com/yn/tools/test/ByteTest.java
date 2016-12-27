package com.yn.tools.test;

import com.yn.tools.monitor.Monitor;
import com.yn.tools.serialize.MsgPackSerialization;
import com.yn.tools.netty.msg.User;
import com.yn.tools.serialize.FastJsonSerialization;
import com.yn.tools.serialize.JacksonSerializer;
import com.yn.tools.serialize.Serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangnan on 16/12/12.
 */
public class ByteTest {
    public static void main(String[] args) throws Exception {

        List<User> users = new ArrayList<User>();
        Serialization jackson = new JacksonSerializer();
        Serialization fastjson = new FastJsonSerialization();
        Serialization msgson = new MsgPackSerialization();

        byte[] jack = null;
        byte[] fast = null;
        byte[] msg = null;
        for (int i=0;i<10000000;i++) {
            User u = new User("i", i);
            users.add(u);
            jack = jackson.serialize(u);
            fast = fastjson.serialize(u);
            msg = msgson.serialize(u);
        }

//        SingleThread(users);

//        MutiplyThread(users);

        UnMutiplyThread(jack, fast, msg);
    }

    public static void UnMutiplyThread(byte[] jack, byte[] fast, byte[] msg) throws InterruptedException {

        int count = 10000000;
        List<byte[]> jacks = new ArrayList<byte[]>(count);

        int num = 1000;
        ExecutorService e1 = Executors.newFixedThreadPool(num);

        CountDownLatch c1 = new CountDownLatch(count);
        CountDownLatch c2 = new CountDownLatch(count);
        CountDownLatch c3 = new CountDownLatch(count);

        for (int i=0; i<count; i++) {
            jacks.add(Arrays.copyOf(jack, jack.length));
        }
        Monitor.begin();
        for (byte[] bs : jacks) {
            e1.submit(new UnSerializationThread(new JacksonSerializer(), bs, c1));
        }
        c1.await();
        Monitor.end("jackson");
        jacks.clear();
        e1.shutdown();

        ExecutorService e2 = Executors.newFixedThreadPool(num);
        List<byte[]> fasts = new ArrayList<byte[]>(count);
        for (int i=0; i<count; i++) {
            fasts.add(Arrays.copyOf(fast, fast.length));
        }
        Monitor.begin();
        for (byte[] bs : fasts) {
            e2.submit(new UnSerializationThread(new FastJsonSerialization(), bs, c2));
        }
        c2.await();
        Monitor.end("fastJson");
        e2.shutdown();

        ExecutorService e3 = Executors.newFixedThreadPool(num);
        List<byte[]> msgs = new ArrayList<byte[]>(count);
        for (int i=0; i<count; i++) {
            msgs.add(Arrays.copyOf(msg, msg.length));
        }
        Monitor.begin();
        for (byte[] bs : msgs) {
            e3.submit(new UnSerializationThread(new MsgPackSerialization(), bs, c3));
        }
        c3.await();
        Monitor.end("MsgPack");
        e3.shutdown();

    }

    public static void SingleThread(List<User> users) {

        Serialization mapper = new JacksonSerializer();
        Serialization fastJson = new FastJsonSerialization();
        Serialization messagePack = new MsgPackSerialization();

        Monitor.begin();
        for (User user : users) {
            System.out.println(mapper.serialize(user));
        }
        Monitor.end("jackson");

        Monitor.begin();
        for (User user : users) {
            System.out.println(fastJson.serialize(user));
        }
        Monitor.end("fastJson");
        Monitor.begin();
        for (User u : users) {
            System.out.println(messagePack.serialize(u));
        }
        Monitor.end("MsgPack");
    }

    public static void MutiplyThread(List<User> users) throws InterruptedException {
        int num = 1000;
        ExecutorService e1 = Executors.newFixedThreadPool(num);

        CountDownLatch c1 = new CountDownLatch(users.size());
        CountDownLatch c2 = new CountDownLatch(users.size());
        CountDownLatch c3 = new CountDownLatch(users.size());

        Monitor.begin();
        for (User user : users) {
            e1.submit(new SerializationThread(new JacksonSerializer(), user, c1));
        }
        c1.await();
        Monitor.end("jackson");
        e1.shutdown();

        ExecutorService e2 = Executors.newFixedThreadPool(num);
        Monitor.begin();
        for (User user : users) {
            e2.submit(new SerializationThread(new FastJsonSerialization(), user, c2));
        }
        c2.await();
        Monitor.end("fastJson");
        e2.shutdown();

        ExecutorService e3 = Executors.newFixedThreadPool(num);
        Monitor.begin();
        for (User u : users) {
            e3.submit(new SerializationThread(new MsgPackSerialization(), u, c3));
        }
        c3.await();
        Monitor.end("MsgPack");
        e3.shutdown();
    }

    static class SerializationThread implements Runnable {

        private Serialization serialization;
        private User u;
        private CountDownLatch countDownLatch;

        public SerializationThread(Serialization serialization, User u, CountDownLatch countDownLatch) {
            this.serialization = serialization;
            this.u = u;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            try {
                serialization.serialize(u);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    static class UnSerializationThread implements Runnable {

        private Serialization serialization;
        private byte[] data;
        private CountDownLatch countDownLatch;

        public UnSerializationThread(Serialization serialization, byte[] data, CountDownLatch countDownLatch) {
            this.serialization = serialization;
            this.countDownLatch = countDownLatch;
            this.data = data;
        }

        public void run() {
            try {
                serialization.deserialize(data, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
