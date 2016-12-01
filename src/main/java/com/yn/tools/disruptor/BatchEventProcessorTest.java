package com.yn.tools.disruptor;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangnan on 2016/11/22.
 */
public class BatchEventProcessorTest {
    public static void main(String[] args) throws InterruptedException {
        //创建一个RingBuffer，注意容量是4。
        RingBuffer<Element> ringBuffer =
                RingBuffer.createSingleProducer(new SimpleEventFactory<Element>(Element.class), 4);

        //创建一个事件处理器。
        BatchEventProcessor<Element> batchEventProcessor =
            /*
             * 注意参数：数据提供者就是RingBuffer、序列栅栏也来自RingBuffer
             * EventHandler使用自定义的。
             */
                new BatchEventProcessor<Element>(ringBuffer,
                        ringBuffer.newBarrier(), new ElementHandler());

        //将事件处理器本身的序列设置为ringBuffer的追踪序列。
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());

        //启动事件处理器。
        new Thread(batchEventProcessor).start();
        //往RingBuffer上发布事件
        for(int i=0;i<10;i++){
            new ProducerWithTranslator(ringBuffer).onData((long) i);
            System.out.println("发布事件["+i+"]");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
