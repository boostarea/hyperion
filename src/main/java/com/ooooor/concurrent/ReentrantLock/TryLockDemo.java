package com.ooooor.concurrent.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author ooooor
 * @Date 2019/2/16 11:58 PM
 **/
public class TryLockDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Runnable runnable = () -> {
            try {
                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    Thread.sleep(3000);
                } else {
                    System.out.println("lock failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " exit");
                }
            }
        };

        new Thread(runnable, "t1").start();
        new Thread(runnable, "t2").start();
    }
}
