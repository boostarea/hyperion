package com.ooooor.concurrent.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 绝大多数情况下,线程数与任务数不对等,每个线程维护任务队列,当空闲时,从其他队列尾部密取消费,达到平衡
 * forkJoinPool使用无锁栈,管理空闲线程
 *
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10000;
    private long start;
    private long end;

    public ForkJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end - start)<THRESHOLD;
        if (canCompute) {
            for(long i=start; i<=end; i++) {
                sum += i;
            }
        } else {
            // 拆分为100个任务
            long step = (start+end)/100;
            List<ForkJoinDemo> subTasks = new ArrayList<>();
            long pos = start;
            for (int i=0; i<100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }

                ForkJoinDemo subTask = new ForkJoinDemo(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (ForkJoinDemo f : subTasks) {
                sum += f.join();
            }
        }
        return sum;
    }

    /**
     * 数值增大,容易造成stackoverflowError
     * @param args
     */
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo task = new ForkJoinDemo(0, 200000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long start = System.currentTimeMillis();
            long res = result.get();
            System.out.println("exec: " + (System.currentTimeMillis() - start) +" sum: " + res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
