package com.yn.tools.threads;

import java.util.concurrent.*;

/**
 * Created by yangnan on 17/5/11.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        int n = 4;
        CyclicBarrier barrier = new CyclicBarrier(n, new Runnable() {
            public void run() {
                System.out.println("当前线程"+Thread.currentThread().getName());
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(n);
        for (int i=0; i<n; ++i) {
            executorService.submit(new CyclicBarrierTest.Writer(barrier));
        }

        TimeUnit.SECONDS.sleep(10);
        System.out.println("CyclicBarrier重用");
        for (int i=0; i<n; ++i) {
            executorService.submit(new CyclicBarrierTest.Writer(barrier));
        }

        executorService.shutdown();
    }

    static class Writer extends Thread {
        private CyclicBarrier barrier;

        public Writer(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程"+threadName+"正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+threadName+"写入数据完毕，等待其他线程写入完毕");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
