package com.ooooor.basic.exchanger;

import java.util.concurrent.Exchanger;

/**
 * @Description 造成阻塞，互相等待，若为奇数个，则永久等待
 * @Author ooooor
 * @Date 2019-04-08 15:19
 **/
public class ExchangerDemo extends Thread {

    private Exchanger<String> exchanger;
    private String string;
    private String threadName;

    public ExchangerDemo(Exchanger<String> exchanger, String string,
                         String threadName) {
        this.exchanger = exchanger;
        this.string = string;
        this.threadName = threadName;
    }

    public void run() {
        try {
            System.out.println(threadName + ": " + exchanger.exchange(string));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        ExchangerDemo test1 = new ExchangerDemo(exchanger, "string1", "thread-1");
        ExchangerDemo test2 = new ExchangerDemo(exchanger, "string2", "thread-2");
        ExchangerDemo test3 = new ExchangerDemo(exchanger, "string3", "thread-3");

        test1.start();
        Thread.sleep(2000L);
        test2.start();
        test3.start();
    }
}
