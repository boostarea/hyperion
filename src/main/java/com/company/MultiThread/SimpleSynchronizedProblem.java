package com.company.MultiThread;

/**
 *
 * Problem:
 * Integer为不可变对象, i++产生一个新实例赋予j.
 *
 *
 */
public class SimpleSynchronizedProblem implements Runnable {

    static Integer j = 0;

    @Override
    public void run() {
        for (; j < 10000000; j++) {
            synchronized (j) {
                j++;   // j=Integer.valueOf(i.intValue() + 1)
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SimpleSynchronizedProblem());
        Thread t2 = new Thread(new SimpleSynchronizedProblem());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(j);
    }

}
