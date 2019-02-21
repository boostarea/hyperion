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

/**
 * @description:
 * @author: chenr
 * @date: 19-2-21
 */
public class WaitNotifyModel implements Model {

    private final Object BUFFER_LOCK = new Object();
    private final Queue<Task> buffer = new LinkedList<>();
    private final int cap;
    private final AtomicInteger increTaskNo = new AtomicInteger(0);
    public WaitNotifyModel(int cap) {
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
            synchronized (BUFFER_LOCK) {
                while (buffer.size() == 0) {
                    BUFFER_LOCK.wait();
                }
                Task task = buffer.poll();
                assert task != null;
                // 固定时间范围的消费，模拟相对稳定的服务器处理过程
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("consume: " + task.getNo());
                BUFFER_LOCK.notifyAll();
            }
        }
    }
    private class ProducerImpl extends AbstractProducer implements Producer, Runnable {
        @Override
        public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long) (Math.random() * 1000));
            synchronized (BUFFER_LOCK) {
                while (buffer.size() == cap) {
                    BUFFER_LOCK.wait();
                }
                Task task = new Task(increTaskNo.getAndIncrement());
                buffer.offer(task);
                System.out.println("produce: " + task.getNo());
                BUFFER_LOCK.notifyAll();
            }
        }
    }
    public static void main(String[] args) {
        Model model = new WaitNotifyModel(3);
        for (int i = 0; i < 2; i++) {
            new Thread(model.newConsumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(model.newProducer()).start();
        }
    }
}
