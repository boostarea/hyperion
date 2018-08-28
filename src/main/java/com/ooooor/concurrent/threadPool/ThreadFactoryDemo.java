package com.ooooor.concurrent.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定制线程池创建工厂
 */
public class ThreadFactoryDemo {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println(System.currentTimeMillis() + " Thread id: " + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getId() + " end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                r -> {
                    Thread thread = new Thread(r);
                    // 守护线程
                    thread.setDaemon(true);
                    System.out.println("create " + thread);
                    return thread;
                });
        for (int i=0; i<5; i++) {
            executorService.submit(runnable);
        }
        // Thread.sleep(2000);
    }

}
