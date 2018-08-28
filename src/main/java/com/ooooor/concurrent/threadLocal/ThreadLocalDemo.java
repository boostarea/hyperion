package com.ooooor.concurrent.threadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {
    // ThreadLocal 只起简单容器作用， 对象由线程注入
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class ParseDate implements Runnable {
        int i;
        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                //首次，为当前线程分配sdf实例
                if (threadLocal.get() == null) {
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                    // 若分配相同实例，还是会有安全问题
                    // threadLocal.set(sdf);
                }
                Date t = threadLocal.get().parse("2015-03-29 19:29:" + i%60);
                System.out.println(i + ": " + t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i<1000; i++) {
            executorService.execute(new ParseDate(i));
        }
    }
}
