package com.ooooor.concurrent.lockSupport;

/**
 * LockSupport 在中断时，不抛出异常，默默吞下
 */
public class LockSupportInterrupted {
    public static Object object = new Object();
    public static LockSupport.ChangeObjectThread t1 = new LockSupport.ChangeObjectThread("t1");
    public static LockSupport.ChangeObjectThread t2 = new LockSupport.ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("in " + getName());
                java.util.concurrent.locks.LockSupport.park();
                if (Thread.interrupted()) {
                    System.out.println(getName() + " interrupted");
                }
            }
            System.out.println(getName() + " end");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        java.util.concurrent.locks.LockSupport.unpark(t2);
    }
}
