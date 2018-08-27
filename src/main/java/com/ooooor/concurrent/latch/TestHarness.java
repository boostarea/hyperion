package com.ooooor.concurrent.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 闭锁：　CountDownLatch
 * 起始门 Starting Gate(确保所有线程都就绪)
 * 结束门 Ending Gate （等待所以线程都完成）
 *
 * Use CountDownLatch to crate a controlled concurrent initialization mechanism.(Expensive resources)
 */
public class TestHarness {

    private final static AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i =0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        try {
            System.out.println(timeTasks(1000, new Thread(() -> System.out.println(ATOMIC_INTEGER.addAndGet(1)))));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
