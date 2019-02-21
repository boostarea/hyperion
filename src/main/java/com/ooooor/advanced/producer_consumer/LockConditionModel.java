package com.ooooor.advanced.producer_consumer;

import com.ooooor.advanced.producer_consumer.inner.AbstractConsumer;
import com.ooooor.advanced.producer_consumer.inner.AbstractProducer;
import com.ooooor.advanced.producer_consumer.inner.Consumer;
import com.ooooor.advanced.producer_consumer.inner.Model;
import com.ooooor.advanced.producer_consumer.inner.Producer;
import com.ooooor.advanced.producer_consumer.inner.Task;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 瓶颈，在同一时刻，condition只允许一个线程操作缓冲区
 * @author: chenr
 * @date: 19-2-21
 */
public class LockConditionModel implements Model {
    private final Lock BUFFER_LOCK = new ReentrantLock();
    private final Condition BUFFER_COND = BUFFER_LOCK.newCondition();
    private final Queue<Task> buffer = new LinkedList<>();
    private final int cap;
    private final AtomicInteger increTaskNo = new AtomicInteger(0);
    public LockConditionModel(int cap) {
        this.cap = cap;
    }

    @Override
    public Runnable newConsumer() {
        return new ConsumerImpl();
    }

    @Override
    public Runnable newProducer() {
        return new ProducerImpl();
    }

    private class ConsumerImpl extends AbstractConsumer implements Consumer, Runnable {
        @Override
        public void consume() throws InterruptedException {
            BUFFER_LOCK.lockInterruptibly();
            try {
                while (buffer.size() == 0) {
                    BUFFER_COND.await();
                }
                Task task = buffer.poll();
                assert task != null;
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + task.getNo());
                BUFFER_COND.signalAll();
            } finally {
                BUFFER_LOCK.unlock();
            }
        }
    }
    private class ProducerImpl extends AbstractProducer implements Producer, Runnable {
        @Override
        public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long) (Math.random() * 1000));
            BUFFER_LOCK.lockInterruptibly();
            try {
                while (buffer.size() == cap) {
                    BUFFER_COND.await();
                }
                Task task = new Task(increTaskNo.getAndIncrement());
                buffer.offer(task);
                System.out.println("produce: " + task.getNo());
                BUFFER_COND.signalAll();
            } finally {
                BUFFER_LOCK.unlock();
            }
        }
    }
    public static void main(String[] args) {
        Model model = new LockConditionModel(3);
        for (int i = 0; i < 2; i++) {
            new Thread(model.newConsumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(model.newProducer()).start();
        }
    }
}
