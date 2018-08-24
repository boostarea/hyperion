package com.company.LockInterruptibly;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 如果线程等待锁,重入锁可中断响应, synchronized只能保持等待或继续执行
 *
 */
public class LockInterruptibly implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public LockInterruptibly(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                 lock1.lockInterruptibly();
                 try {
                     Thread.sleep(500);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
            }
            System.out.println(Thread.currentThread().getName() + " exit");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockInterruptibly r1 = new LockInterruptibly(1);
        LockInterruptibly r2 = new LockInterruptibly(2);

        Thread t1 = new Thread(r1, "r1");
        Thread t2 = new Thread(r2, "r2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
    }
}
