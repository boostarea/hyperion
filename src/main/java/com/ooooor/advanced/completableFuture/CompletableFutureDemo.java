package com.ooooor.advanced.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: chenr
 * @date: 19-2-22
 */
public class CompletableFutureDemo {

    static CompletableFuture<Integer> future = new CompletableFuture<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            int re = 0;
            try {
                re = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println(re);
        }).start();

        Thread.sleep(3000);
        future.complete(60);
    }
}
