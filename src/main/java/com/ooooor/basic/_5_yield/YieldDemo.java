package com.ooooor.basic._5_yield;

/**
 * @description: 谦让
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

                    /* causes the currently executing thread object to temporarily pause and allow other threads to execute */
                    Thread.yield();
                }
            }
            System.out.println(Thread.currentThread().getName() + " has finished executing.");
        };
        new Thread(runnable, "t1").start();
        new Thread(runnable, "t2").start();
        new Thread(runnable, "t3").start();
    }
}
