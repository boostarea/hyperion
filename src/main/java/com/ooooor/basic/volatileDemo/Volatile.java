package com.ooooor.basic.volatileDemo;

/**
 * -server模式,进行足够优化,导致指令重排
 */
public class Volatile {

    private static boolean ready;
    // private volatile static boolean ready;

    /**
     * -server下, 可能无法输出
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while(!ready);
            System.out.println("end");
        }).start();
        Thread.sleep(1000);
        ready=true;
        Thread.sleep(1000);
    }
}
