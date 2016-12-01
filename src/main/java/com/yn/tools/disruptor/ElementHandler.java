package com.yn.tools.disruptor;

import com.lmax.disruptor.EventHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yangnan on 2016/11/22.
 */
public class ElementHandler implements EventHandler<Element> {

    private Set<Long> ids;

    public ElementHandler(Set<Long> ids) {
        this.ids = ids;
    }

    public ElementHandler() {
        this.ids = new HashSet<Long>();
    }

    public void onEvent(Element element, long l, boolean b) throws Exception {
//        Thread.sleep(10000);
        System.out.println("handle event's data:[id=" + element.getValue() + ",l=" + l + ",delay=" + (System
                .currentTimeMillis() - element.getCurrentTime()) + " " +
                "], " +
                "isEndOfBatch=" + b);
        ids.add(Thread.currentThread().getId());
    }
}
