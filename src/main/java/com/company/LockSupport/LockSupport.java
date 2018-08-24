package com.company.LockSupport;

/**
 * 线程阻塞工具类
 *   park()/unpark()
 *   类似于信号量,但每个线程只有一个许可, 许可可用,park立刻返回
 *   unpack发生在pack之前,也不会产生suspend问题
 *
 */
public class LockSupport {
    public static Object object = new Object();
    public static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    public static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("in " + getName());
                java.util.concurrent.locks.LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        java.util.concurrent.locks.LockSupport.unpark(t1);
        java.util.concurrent.locks.LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
