package com.ooooor.advanced.producer_consumer.inner;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-21
 */
public interface Model {
    Runnable newConsumer();
    Runnable newProducer();
}
