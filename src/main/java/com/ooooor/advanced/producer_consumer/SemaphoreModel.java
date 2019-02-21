package com.ooooor.advanced.producer_consumer;

import com.ooooor.advanced.producer_consumer.inner.AbstractConsumer;
import com.ooooor.advanced.producer_consumer.inner.AbstractProducer;
import com.ooooor.advanced.producer_consumer.inner.Model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-21
 */
public class SemaphoreModel implements Model {
    private int CAP = 10;
    //创建三个信号量
    final Semaphore notFull = new Semaphore(CAP);
    final Semaphore notEmpty = new Semaphore(0);
    final Semaphore mutex = new Semaphore(1);
    private final AtomicInteger increNo = new AtomicInteger(0);


    @Override
    public Runnable newConsumer() {
        return new ConsumerImpl();
    }

    @Override
    public Runnable newProducer() {
        return new ProducerImpl();
    }

    private class ConsumerImpl extends AbstractConsumer {
        @Override
        public void consume() throws InterruptedException {
            try {
                notEmpty.acquire();
                mutex.acquire();
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + increNo.decrementAndGet());
            } finally {
                mutex.release();
                notFull.release();
            }
        }
    }

    private class ProducerImpl extends AbstractProducer {
        @Override
        public void produce() throws InterruptedException {
            try {
                System.out.println("produce: " + increNo.incrementAndGet());
                notFull.acquire();
                mutex.acquire();
                Thread.sleep((long) (Math.random() * 1000));
            } finally {
                mutex.release();
                notEmpty.release();
            }
        }
    }

    public static void main(String[] args) {
        Model model = new SemaphoreModel();
        for (int i = 0; i < 2; i++) {
            new Thread(model.newConsumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(model.newProducer()).start();
        }
    }
}
