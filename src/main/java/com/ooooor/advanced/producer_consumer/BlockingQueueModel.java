package com.ooooor.advanced.producer_consumer;

import com.ooooor.advanced.producer_consumer.inner.AbstractConsumer;
import com.ooooor.advanced.producer_consumer.inner.AbstractProducer;
import com.ooooor.advanced.producer_consumer.inner.Model;
import com.ooooor.advanced.producer_consumer.inner.Producer;
import com.ooooor.advanced.producer_consumer.inner.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 并发和容量控制都封装在BlockingQueue中，核心代码take()/put()
 * @author: chenr
 * @date: 19-2-21
 */
public class BlockingQueueModel implements Model {
    private final BlockingQueue<Task> queue;
    private final AtomicInteger increNo = new AtomicInteger(0);
    public BlockingQueueModel(int cap) {
        this.queue = new LinkedBlockingQueue<>(cap);
    }

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
            Task task = queue.take();
            // 固定时间范围的消费，模拟相对稳定的服务器处理过程
            Thread.sleep(500 + (long) (Math.random() * 500));
            System.out.println("consume: " + task.getNo());
        }
    }

    private class ProducerImpl extends AbstractProducer implements Producer, Runnable {
        @Override
        public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long) (Math.random() * 1000));
            Task task = new Task(increNo.getAndIncrement());
            queue.put(task);
            System.out.println("produce: " + task.getNo());
        }
    }
    public static void main(String[] args) {
        Model model = new BlockingQueueModel(3);
        for (int i = 0; i < 2; i++) {
            new Thread(model.newConsumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(model.newProducer()).start();
        }
    }

    // Task
    // Producer/Consumer Runnabl
}
