package com.ooooor.basic._6_threadGroup;

/**
 * @description: 线程组，便于管理
 * @author: chenr
 * @date: 19-2-15
 */
public class ThreadGroupDemo {

    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("group");

        Runnable runnable = () -> {
            String name = Thread.currentThread().getThreadGroup().getName() + "_" + Thread.currentThread().getName();
            while (true) {
                System.out.println(name);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(group, runnable, "t1").start();
        new Thread(group, runnable, "t2").start();
        System.out.println(group.activeCount());
        group.list();
    }
}
