package com.ooooor.concurrent.threadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class ThreadLocalGCDemo {

    private static volatile  ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
        protected void finalize() {
            System.out.println(this.toString() + " is gc");
        }
    };
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(10000);

    public static class ParseDate implements Runnable {
        int i;
        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (t1.get() == null) {
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        protected void finalize() {
                            System.out.println(this.toString() + " is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + " :create SimpleDateFormat");
                }
                Date date = t1.get().parse("2018-08-29 19:08:" + i%60);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(new ParseDate(i));
        }

        countDownLatch.await();
        System.out.println("mission complete");
        t1=null;
        System.gc();
        System.out.println("first GC complete");


        t1 = new ThreadLocal<>();
        countDownLatch = new CountDownLatch(10000);
        for (int i =0; i < 10000; i++) {
            executorService.execute(new ParseDate(i));
        }
        countDownLatch.await();
        Thread.sleep(100);
        System.gc();
        System.out.println("second GC complete");
    }

}
