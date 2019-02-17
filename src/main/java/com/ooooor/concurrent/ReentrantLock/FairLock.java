package com.ooooor.concurrent.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author ooooor
 * @Date 2019/2/17 1:22 PM
 **/
public class FairLock {

    public static ReentrantLock fairLock = new ReentrantLock(true);

    public static void main(String[] args) {
        Runnable runnable = () -> {
            while (true) {
                try {
                    fairLock.lock();
                    System.out.println(Thread.currentThread().getName());
                } finally {
                    fairLock.unlock();
                }
            }
        };

        new Thread(runnable, "t1").start();
        new Thread(runnable, "t2").start();
    }
}
