package com.ooooor.concurrent.barrier;

import com.sun.tools.corba.se.idl.constExpr.Or;

import javax.sound.midi.Soundbank;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description 计数器可以反复使用，凑齐线程数后，计数器归零。
 * @Author ooooor
 * @Date 2019/2/17 1:56 PM
 **/
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        Soldier(CyclicBarrier cyclicBarrier, String soldier) {
            this.cyclicBarrier = cyclicBarrier;
            this.soldier = soldier;
        }

        void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // 第一个栅栏，等待所有士兵到齐
                cyclicBarrier.await();
                doWork();
                // 第二个栅栏，等待士兵都完成工作
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;
        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        public void run() {
            if (flag) {
                System.out.println("soldier " + N + "个，任务完成");
            } else {
                System.out.println("soldier " + N + "个，集合完毕");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));

        System.out.println("集合队伍");
        for (int i = 0; i < N; ++i) {
            System.out.println("soldier" + i + "报道");
            allSoldier[i] = new Thread(new Soldier(cyclicBarrier, "soldier" + i));
            allSoldier[i].start();
        }

    }
}
