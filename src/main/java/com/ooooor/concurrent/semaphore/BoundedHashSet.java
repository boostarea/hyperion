package com.ooooor.concurrent.semaphore;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore实现有界容器
 *
 * Using in Resource Pool
 *
 * @param <T>
 */
public class BoundedHashSet<T> {

    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        this.set = new ConcurrentSkipListSet<T>();
        semaphore = new Semaphore(bound);
    }

    public boolean add(T t) throws InterruptedException {
        semaphore.acquire();
        System.out.println(t);
        boolean wasAdded = false;
        try {
            wasAdded = set.add(t);
            return wasAdded;
        } finally {
            Thread.sleep(500);
            semaphore.release();
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) semaphore.release();
        return wasRemoved;
    }

    public static void main(String[] args) throws Exception {
        final BoundedHashSet boundedHashSet = new BoundedHashSet<Integer>(3);

        for (int i=0; i<1000; i++) {
            final int finalI = i;
            Thread thread = new Thread() {
                public void run() {
                    try {
                        boundedHashSet.add(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            // new Thread(() -> {
            //
            // }).start();
        }
    }
}
