package com.yn.tools.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * Created by yangnan on 2016/11/29.
 */
public class ElementWorker implements WorkHandler<Element> {

    private int id;

    public ElementWorker(int id) {
        this.id = id;
    }

    public void onEvent(Element element) throws Exception {
        if (element.getValue() == 0) {
            Thread.sleep(1000 * 60);
        }
        System.out.println("WorkHandler-" + id + " event's data:[id=" + element.getValue() + ",delay=" + (System
                .currentTimeMillis() - element.getCurrentTime()) + ",threadId= " + Thread.currentThread().getId() +
                ",element=" + element +
                "]");
    }
}
