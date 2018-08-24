package com.company.MultiThread;

import java.util.Map;

/**
 * @description:
 * @author: chenr
 * @date: 18-8-23
 */
public class HashMap {
    private static Map<String, String> map = new java.util.HashMap<>();

    public static class AddThread implements Runnable {
        int start = 0;
        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < 100000; i+=2) {
                map.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread(0));
        Thread t2 = new Thread(new AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}
