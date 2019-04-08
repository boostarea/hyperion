package com.ooooor.basic.phaser;

import java.util.concurrent.Phaser;

/**
 * @Description onAdvance()
 * @Author ooooor
 * @Date 2019-04-08 16:57
 **/
public class PhaserTest_onAdvance {

    /*
    task_2开始执行任务...
    task_1开始执行任务...
    task_0开始执行任务...
    执行onAdvance方法.....;phase:0 registeredParties=3
    task_0结束任务
    task_2结束任务
    task_1结束任务

    task_1开始执行任务...
    task_2开始执行任务...
    task_0开始执行任务...
    执行onAdvance方法.....;phase:1 registeredParties=3
    task_0结束任务
    task_2结束任务
    task_1结束任务

    task_2开始执行任务...
    task_0开始执行任务...
    task_1开始执行任务...
    执行onAdvance方法.....;phase:2 registeredParties=3
    task_1结束任务
    task_0结束任务
    task_2结束任务

    task_0开始执行任务...
    task_2开始执行任务...
    task_1开始执行任务...
    执行onAdvance方法.....;phase:3 registeredParties=3
    task_1结束任务
    task_0结束任务
    task_2结束任务

    task_1开始执行任务...
    task_0开始执行任务...
    task_2开始执行任务...
    执行onAdvance方法.....;phase:4 registeredParties=3
    task_2结束任务
    task_1结束任务
    task_0结束任务

    task_2开始执行任务...
    task_1开始执行任务...
    task_0开始执行任务...
    执行onAdvance方法.....;phase:5 registeredParties=3
    task_0结束任务
    task_1结束任务
    task_2结束任务

    task_1开始执行任务...
    task_2开始执行任务...
    task_0开始执行任务...
    执行onAdvance方法.....;phase:6 registeredParties=3
    task_0结束任务
    task_2结束任务
    task_1结束任务
     */
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(3) {
            /**
             * registeredParties:线程注册的数量
             * phase:进入该方法的线程数，
             */
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("执行onAdvance方法.....;phase:" + phase + " registeredParties=" + registeredParties);
                return phase == 6;
            }
        };

        for (int i = 0; i < 3; i++) {
            Task_03 task = new Task_03(phaser);
            Thread thread = new Thread(task, "task_" + i);
            thread.start();
        }
    }

    static class Task_03 implements Runnable {
        private final Phaser phaser;

        public Task_03(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始执行任务...");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + "结束任务");
            } while (!phaser.isTerminated());
        }
    }
}
