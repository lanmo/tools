package com.yn.tools.singleton;

import java.io.Serializable;

/**
 * Created by yangnan on 16/12/21.
 * 使用双重校验锁方式实现单例
 */
public class Singleton implements Serializable {

    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {

        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    /**
     * 防止序列化破坏单例模式
     * @return
     */
    private Object readResolve() {
        return singleton;
    }
}