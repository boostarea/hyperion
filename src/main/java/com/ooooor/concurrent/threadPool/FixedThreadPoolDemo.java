package com.ooooor.concurrent.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 为何同一批线程,时间略有波动 ??
 *
 * Output:
 * 1535334631777 Thread Id11
 * 1535334631778 Thread Id13
 * 1535334631780 Thread Id15
 * 1535334632777 Thread Id11
 * 1535334632778 Thread Id13
 * 1535334632781 Thread Id15
 * 1535334633777 Thread Id11
 * 1535334633779 Thread Id13
 * 1535334633781 Thread Id15
 * 1535334634778 Thread Id11
 */
public class FixedThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            executor.submit(new Thread(() -> {
                System.out.println(System.currentTimeMillis() + " Thread Id" + Thread.currentThread().getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
    }
}
