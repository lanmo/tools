package com.yn.tools.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by yangnan on 2016/11/22.
 */
public class SimpleEventFactory<T> implements EventFactory<T> {
    private Class<T> tClass;

    public SimpleEventFactory(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T newInstance() {
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
