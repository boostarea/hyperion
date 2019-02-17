package com.ooooor.concurrent.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *  synchronized不响应中断
 *
 */
public class SynchronizedInterruptibly implements Runnable {
    public static Object lock1 = new ReentrantLock();
    public static Object lock2 = new ReentrantLock();
    int lock;

    public SynchronizedInterruptibly(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                synchronized (lock1) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock2) {

                    }
                }
            } else {
                synchronized (lock2) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock1) {

                    }
                }
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + " exit");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedInterruptibly r1 = new SynchronizedInterruptibly(1);
        SynchronizedInterruptibly r2 = new SynchronizedInterruptibly(2);

        Thread t1 = new Thread(r1, "r1");
        Thread t2 = new Thread(r2, "r2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
        Thread.sleep(1000);
    }
}
