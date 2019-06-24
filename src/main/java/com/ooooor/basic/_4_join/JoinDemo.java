package com.ooooor.basic._4_join;

/**
 * @description: _4_join()等待完成，可设置最长等待时间
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
        thread.join(1);
        // thread._4_join();
        System.out.println(i);
    }

}
