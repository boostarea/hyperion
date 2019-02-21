package com.ooooor.basic.daemon;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-15
 */
public class DaemonDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 系统中只有主线程main为用户线程
        // thread.setDaemon(true);
        thread.start();
        Thread.sleep(2000);
    }
}
