package com.ooooor.basic.phaser;

import java.util.concurrent.Phaser;

/**
 * @Description awaitAdvance(0):countDownLatch.await(); arrive():countDownLatch.countDown()
 * Phaser是一个更加复杂和强大的同步辅助类，它允许并发执行多阶段任务。当我们有并发任务并且需要分解成几步执行时，（CyclicBarrier是分成两步），就可以选择使用Phaser。
 * @Author ooooor
 * @Date 2019-04-08 15:59
 **/
public class PhaserTest_awaitAdvance {

    /*
    PhaseTest_2执行任务...
    PhaseTest_1执行任务...
    PhaseTest_0执行任务...
     */
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);        //CountDownLatch(1)

        for (int i = 0; i < 3; i++) {
            Task_05 task = new Task_05(phaser);
            Thread thread = new Thread(task, "PhaseTest_" + i);
            thread.start();
        }

        try {
            //等待3秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phaser.arrive();        //countDownLatch.countDown()
    }

    static class Task_05 implements Runnable {
        private final Phaser phaser;

        Task_05(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(phaser.getPhase());
            phaser.awaitAdvance(phaser.getPhase());        //countDownLatch.await()
            System.out.println(Thread.currentThread().getName() + "执行任务...");
        }
    }
}
