package com.ooooor.concurrent.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 1. scheduleAtFixedRate()  间隔相同频率
 * 2. scheduleWithFixedDelay()  延迟相同频率
 *
 */
public class ScheduleThreadPoolDemo {

    /**
     * Output:
     *  1535339788
     *  1535339790
     *  1535339792
     *  1535339794
     */
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        // 任务在两秒未执行完,不会开始下次调度(e.g run 8s, every schedule 8s)
        service.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(System.currentTimeMillis() / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
