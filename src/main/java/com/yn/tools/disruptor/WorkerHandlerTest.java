package com.yn.tools.disruptor;

import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WorkerPool;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by yangnan on 2016/11/29.
 */
public class WorkerHandlerTest {
    public static void main(String[] args) {
        //创建一个RingBuffer，注意容量是4。
        RingBuffer<Element> ringBuffer =
                RingBuffer.createSingleProducer(new SimpleEventFactory<Element>(Element.class), 4, new SleepingWaitStrategy());
        //创建3个WorkHandler
        ElementWorker handler1 = new ElementWorker(1);
        ElementWorker handler2 = new ElementWorker(2);
        ElementWorker handler3 = new ElementWorker(3);
        WorkerPool<Element> workerPool =
                new WorkerPool<Element>(ringBuffer, ringBuffer.newBarrier(),
                        new IgnoreExceptionHandler(),
                        handler1,handler2,handler3);

        //将WorkPool的工作序列集设置为ringBuffer的追踪序列。
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        //创建一个线程池用于执行Workhandler。
        Executor executor = Executors.newFixedThreadPool(4);
        //启动WorkPool。
        workerPool.start(executor);
        //往RingBuffer上发布事件
        for(int i=0; i<10; i++){
            new ProducerWithTranslator(ringBuffer).onData((long) i);
            System.out.println("发布事件["+i+"]");
//            try {
//                Thread.sleep(1000 * 10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
