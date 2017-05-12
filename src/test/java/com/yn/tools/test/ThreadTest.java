package com.yn.tools.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangnan on 17/4/14.
 */
public class ThreadTest {

    static final Map<Integer, Integer> map = new HashMap<Integer, Integer>(2, 1f);

    public static void main(String[] args) throws InterruptedException {

            map.put(5, 55);

            new Thread("Thread1") {
                public void run() {
                    map.put(7, 77);
                    System.out.println(map);
                };
            }.start();

            new Thread("Thread2") {
                public void run() {
                    map.put(3, 33);
                    System.out.println(map);
                };
            }.start();

    }

    static class MyTnread implements Runnable {
        long a = 1;
        boolean v = false;
        public void run() {
            while (true) {
                System.out.println(a++);
                try {
                    if (!v) {
                        Thread.sleep(100000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    v = true;
                }
            }
        }
    }
}
