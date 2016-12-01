package com.yn.tools.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * Created by yangnan on 2016/11/22.
 */
public class ElementProducer {

    private final RingBuffer<Element> ringBuffer;

    public ElementProducer(RingBuffer<Element> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long value) {
        long sequence = ringBuffer.next();
        try {
            Element element = ringBuffer.get(sequence);
            element.setValue(value);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
