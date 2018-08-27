package com.ooooor.concurrent.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义拒绝策略
 */
public class RejectThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println(System.currentTimeMillis() + " Thread id: " + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10),
                // new LinkedBlockingDeque<>(),
                // new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                (r, executor) -> System.out.println(r.toString() + " is discard"));

        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.submit(runnable);
            Thread.sleep(10);
        }
    }
}
