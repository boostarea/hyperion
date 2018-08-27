package com.ooooor.basic.lockIssues.fairLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁实现成本高,默认情况,使用非公平锁
 *
 */
public class FairLock implements Runnable {
    static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread() + "Get");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock r1 = new FairLock();
        new Thread(r1, "r1").start();
        new Thread(r1, "r2").start();
    }
}
