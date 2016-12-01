package com.yn.tools.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * Created by yangnan on 2016/11/29.
 */
public class ProducerWithTranslator extends AbstractProducerWithTranslator<Element, Long> {

    public ProducerWithTranslator(RingBuffer<Element> ringBuffer) {
        super(ringBuffer);
    }

    public void translator(Element target, long l, Long aLong) {
        System.out.println("translator:[target=" + target + ",l=" + l +",aLong=" + aLong + "]" );
        target.setValue(aLong);
        target.setCurrentTime(System.currentTimeMillis());
    }
}
