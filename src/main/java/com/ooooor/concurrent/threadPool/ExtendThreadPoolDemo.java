package com.ooooor.concurrent.threadPool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExtendThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            System.out.println("excuting Thread ID: " + Thread.currentThread().getId() + ",Task Name=" + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("before " + t.getName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("after ");
            }

            @Override
            protected void terminated() {
                System.out.println("terminated");
            }
        };

        for (int i=0; i<5; i++) {
            executorService.execute(runnable);
            Thread.sleep(10);
        }
        executorService.shutdown();
    }
}
