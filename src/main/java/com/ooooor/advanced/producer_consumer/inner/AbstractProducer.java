package com.ooooor.advanced.producer_consumer.inner;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-21
 */
public abstract class AbstractProducer implements Producer,Runnable {

    @Override
    public void run() {
        while(true) {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
