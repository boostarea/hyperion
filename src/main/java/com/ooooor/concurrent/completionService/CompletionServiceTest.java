package com.ooooor.concurrent.completionService;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * completionService 整合Executor和BlockingQueue
 *
 */
public class CompletionServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        for(int i=0; i < 100; i++) {
            final int c = i;
            completionService.submit(() -> {
                Thread.sleep(1000);
                return String.valueOf(c);
            });
        }

        for(int i=0; i < 100; i++) {
            Future<String> take = completionService.take();
            System.out.println(take.get());
        }
        executorService.shutdown();

    }

}
