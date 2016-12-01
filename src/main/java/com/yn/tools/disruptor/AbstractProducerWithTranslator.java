package com.yn.tools.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * Created by yangnan on 2016/11/22.
 */
public abstract class AbstractProducerWithTranslator<D,S> {

    private final RingBuffer<D> ringBuffer;

    public AbstractProducerWithTranslator(RingBuffer<D> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private final EventTranslatorOneArg<D, S> translator = new EventTranslatorOneArg<D, S>() {
        public void translateTo(D target, long l, S aLong) {
            translator(target, l, aLong);
        }
    };

    public abstract void translator(D target, long l, S aLong);

    public void onData(S s) {
        ringBuffer.publishEvent(translator, s);
    }
}
