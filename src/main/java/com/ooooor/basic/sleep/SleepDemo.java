package com.ooooor.basic.sleep;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-14
 */
public class SleepDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("interrupted exit");
                    break;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("interruped when sleep");
                    // sleep()由于中断抛出异常，会清除中断标记位，所以需要再次设置
                    Thread.currentThread().interrupt();
                }
            }
        });

        t1.start();
        Thread.sleep(100);
        t1.interrupt();
    }
}
