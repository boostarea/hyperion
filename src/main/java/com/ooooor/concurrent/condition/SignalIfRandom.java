package com.ooooor.concurrent.condition;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SignalIfRandom {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i< 1000; i++) {
            final int j = i;
            new Thread(() -> {
                try {
                    lock.lock();
                    condition.await();
                    System.out.println("index:" + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }

        Thread.sleep(2000);

        lock.lock();
        condition.signalAll();
        lock.unlock();
    }
}
