package com.ooooor.basic.suspendError;

/**
 * output:
 * in t1
 * in t2
 *
 * 程序并不会退出，t2的resume在suspend之前被执行，t2被永久挂起
 */
public class BadSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                Thread.currentThread().suspend();
                System.out.println("out " + getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.resume();
        // Thread._2_sleep(100);
        t2.resume();
        t1.join();
        t2.join();
    }

}
