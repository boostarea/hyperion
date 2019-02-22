package com.ooooor.advanced.producer_consumer;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-21
 */
public class PipedModel {

    final PipedInputStream pis = new PipedInputStream();
    final PipedOutputStream pos = new PipedOutputStream();
    private final AtomicInteger increTaskNo = new AtomicInteger(0);
    {
        try {
            pis.connect(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                while(true) {
                    Thread.sleep((long) (Math.random() * 1000));
                    int num = increTaskNo.incrementAndGet();
                    System.out.println("produce：" + num);
                    pos.write(num);
                    pos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pos.close();
                    pis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while(true) {
                    int num = pis.read();
                    Thread.sleep(500 + (long) (Math.random() * 500));
                    System.out.println("consume：" + num);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pos.close();
                    pis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        PipedModel test5 = new PipedModel();
        // new Thread(test5.new Consumer()).start();
        // new Thread(test5.new Producer()).start();

        for (int i = 0; i < 2; i++) {
            new Thread(test5.new Consumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(test5.new Producer()).start();
        }

    }


}
