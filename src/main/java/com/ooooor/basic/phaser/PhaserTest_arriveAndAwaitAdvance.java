package com.ooooor.basic.phaser;

import java.util.concurrent.Phaser;

/**
 * @Description arriveAndAwaitAdvance,阻塞等待其他任务都完成，到达后，再继续进行
 * @Author ooooor
 * @Date 2019-04-08 15:54
 **/
public class PhaserTest_arriveAndAwaitAdvance {
    /*
    PhaseTest_0执行任务完成，等待其他任务执行......
    PhaseTest_3执行任务完成，等待其他任务执行......
    PhaseTest_2执行任务完成，等待其他任务执行......
    PhaseTest_4执行任务完成，等待其他任务执行......
    PhaseTest_1执行任务完成，等待其他任务执行......
    PhaseTest_1继续执行任务...
    PhaseTest_4继续执行任务...
    PhaseTest_2继续执行任务...
    PhaseTest_0继续执行任务...
    PhaseTest_3继续执行任务...
     */
    public static void main(String[] args) {
        Phaser phaser = new Phaser(5);

        for(int i = 0 ; i < 5 ; i++){
            Task_01 task_01 = new Task_01(phaser);
            Thread thread = new Thread(task_01, "PhaseTest_" + i);
            thread.start();
        }
    }

    static class Task_01 implements Runnable{
        private final Phaser phaser;

        public Task_01(Phaser phaser){
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
            //等待其他任务执行完成
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + "继续执行任务...");
        }
    }
}
