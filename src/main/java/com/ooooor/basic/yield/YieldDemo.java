package com.ooooor.basic.yield;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-15
 */
public class YieldDemo {

    public static void main(String[] args) {

        Runnable runnable = () -> {
            for (int i = 0; i < 5; i++) {
                // yields control to another thread every 5 iterations
                if ((i % 5) == 0) {
                    System.out.println(Thread.currentThread().getName() + " yielding control...");

            /* causes the currently executing thread object to temporarily
            pause and allow other threads to execute */
                    Thread.yield();
                }
            }

            System.out.println(Thread.currentThread().getName() + " has finished executing.");
        };

        Thread t1 = new Thread(runnable, "t1");
        Thread t2 = new Thread(runnable, "t2");
        Thread t3 = new Thread(runnable, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
