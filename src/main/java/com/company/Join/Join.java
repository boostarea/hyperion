package com.company.Join;


public class Join {
    public volatile static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (; i < 10000; i++) {
                System.out.println(i);
            }
        });
        // 当只剩下守护线程, 虚拟机自动退出,main为用户线程
        // thread.setDaemon(true);
        thread.start();
        thread.join();
        System.out.println("end");
    }

}
