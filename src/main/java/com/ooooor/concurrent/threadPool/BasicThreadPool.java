package com.ooooor.concurrent.threadPool;

/**
 * BlockingQueue<Runnable> workQueue:
 *                                                      activity threads < corePoolSize | activity thread = corePoolSize && queue not fill up | queue fill up && activity thread < maxPoolSize | queue fill up && activity thread = maxPoolSize
 *      1. 有界队列ArrayBlockingQueue/LinkedBlockingQueue(capacity)     创建新线程          |                   加入队列                            |                  创建新线程                      |                     执行拒绝策略
 *      2. 无界队列LinkedBlockingQueue/PriorityBlockingQueue            创建新线程          |                    加入队列,直到耗尽系统内存(通过使用有界队列解决)
 *      3. 同步队列SynchronousQueue                      总是创建新线程,直到==maxPoolSize, 执行拒绝策略
 *
 * RejectedExecutionHandler handler:
 *      1. AbortPolicy 默认 throws a RejectedExecutionException
 *      2. CallerRunsPolicy 线程池未关闭, 在调用者线程中运行被丢弃任务 执行run()
 *      3. DiscardOledestPolicy 丢弃最老的请求 queue poll()
 *      4. DiscardPolicy 不予处理,默默丢弃
 *
 * Executors:
 *      1. newSingleThreadExecutor(): core=max=0, use LinkedBlockingQueue
 *      2. newFixedThreadPool(): core=max=fix, use LinkedBlockingQueue
 *      3. newCachedThreadPool(): max=Integer.MAX_VALUE, keepAliveTime is 60s use SynchronousQueue
 *      4. newScheduledThreadPool():  max=Integer.MAX_VALUE, used DelayedWorkQueue
 *
 *      newWorkStealingPool
 *
 * threads = Ncpu * Ucpu * (1 + W/C)
 *           CPU数  使用率[0,1]   等待时间/计算时间
 */
public class BasicThreadPool {
}
