package com.yn.tools.singleton;

import java.io.Serializable;

/**
 * Created by yangnan on 2016/12/21.
 * 内部类实现的单例
 */
public class SingletonInner implements Serializable {
    private static class SingletonInnerHolder {
        private static SingletonInner INSTANCE = new SingletonInner();
    }

    public static SingletonInner getInstance() {
        return SingletonInnerHolder.INSTANCE;
    }

    /**
     * 防止序列化破坏单例模式
     * @return
     */
    private Object readResolve() {
        return SingletonInnerHolder.INSTANCE;
    }
}
