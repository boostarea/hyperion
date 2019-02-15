package com.ooooor.basic.join;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-15
 */
public class JoinDemo {

    private volatile static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for(;i<1000000;i++);
        });
        thread.start();
        // thread.join(1);
        thread.join();
        System.out.println(i);
    }

}
