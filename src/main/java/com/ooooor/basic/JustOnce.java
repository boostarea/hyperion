package com.ooooor.basic;

/**
 * @Description 线程属于一次性用品，同一线程多次调用，抛出IllegalThreadStateException
 * @Author ooooor
 * @Date 2019-06-24 19:04
 **/
public class JustOnce {

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> {
            System.out.println("a");
        });
        a.start();
        Thread.sleep(1000L);
        a.start();
    }
}
