package com.company.MultiThread;

import java.util.List;

/**
 * @description:
 * @author: chenr
 * @date: 18-8-23
 */
public class ArrayList {
    private static List<Integer> list = new java.util.ArrayList<>();

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i=0; i < 100000; i++) {
                list.add(i);
            }
        }
    }

    /**
     * output:
     *    or: 184079
     *    or: Exception in thread "Thread-0" java.lang.ArrayIndexOutOfBoundsException: 14053   //多线程扩容,导致越界
     *    or: 200000
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(list.size());
    }
}
