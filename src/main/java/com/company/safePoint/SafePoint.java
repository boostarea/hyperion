package com.company.safePoint;

/**
 *
 *  JVM 中一条特殊线程 VM Threads, 执行特殊的 VM Operation (分派GC的STW, thread dump), 这些任务，都需要整个Heap，以及所有线程的状态是静止的，一致的才能进行.
 *  所以JVM引入了安全点(Safe Point)的概念，想办法在需要进行VM Operation时，通知所有的线程进入一个静止的安全点。
 *
 *  -XX:+PrintGCApplicationStoppedTime 很重要，一定要打开。全部的JVM停顿时间（不只是GC），打印在GC日志里
 *  那些很多但又很短的安全点，全都是RevokeBias,"-XX:-UseBiasedLocking"取消掉它.
 *
 *  轻量级锁通过CAS避免开销大的互斥操作, 偏向锁无cas;
 *偏向锁，顾名思义，它会偏向于第一个访问锁的线程，如果在接下来的运行过程中，该锁没有被其他的线程访问，则持有偏向锁的线程将永远不需要触发同步。
 如果在运行过程中，遇到了其他线程抢占锁，则持有偏向锁的线程会被挂起，JVM会尝试消除它身上的偏向锁，将锁恢复到标准的轻量级锁。(偏向锁只能在单线程下起作用);

 在JDK6中，偏向锁是默认启用的。它提高了单线程访问同步资源的性能。
 但试想一下，如果你的同步资源或代码一直都是多线程访问的，那么消除偏向锁这一步骤对你来说就是多余的。事实上，消除偏向锁的开销还是蛮大的。

 所以在你非常熟悉自己的代码前提下，大可禁用偏向锁 -XX:-UseBiasedLocking 。

 偏向锁替换mark word中threadID为当前线程(偏向);
 偏向模式和非偏向模式，在下面的mark word表中，主要体现在thread ID字段是否为空。


 ???轻量级锁lock record;


 *
 *
 *
 */
public class SafePoint {
}
