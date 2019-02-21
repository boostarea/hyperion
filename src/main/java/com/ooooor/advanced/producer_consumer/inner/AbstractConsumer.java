package com.ooooor.advanced.producer_consumer.inner;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-21
 */
public abstract class AbstractConsumer implements Consumer,Runnable {

    @Override
    public void run() {
        while(true) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
