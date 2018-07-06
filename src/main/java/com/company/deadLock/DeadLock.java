package com.company.deadLock;


/**
 *
 * 1. 过度使用加锁,导致锁顺序死锁(Lock-Ordering DeadLock)
 * 2. 使用线程池和信号量来限制对资源使用,导致资源死锁(Resource DeadLock)
 *
 * "哲学家进餐问题"
 *
 * 互相等待对方锁,最简单死锁形式(抱死Deadly Embrace)
 *
 *
 *
 *
 *
 */
public class DeadLock {

    // 锁顺序死锁
    class LeftRightDeadLock {
        private final Object left = new Object();
        private final Object right = new Object();

        public void leftRight() {
            synchronized (left) {
                synchronized (right) {
                    System.out.println("leftRight");
                }
            }
        }

        public void rightLeft() {
            synchronized (right) {
                synchronized (left) {
                    System.out.println("rightLeft");
                }
            }
        }

    }

    // 动态锁顺序死锁


}
