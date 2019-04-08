package com.ooooor.concurrent.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 游戏多关卡例子
 * CountDownLatch里的线程是到了运行的目标后继续干自己的其他事情，而这里的线程需要等待其他线程后才能继续完成后面的工作。
 * @Author ooooor
 * @Date 2019-04-08 16:19
 **/
public class CyclicBarrierTest {
    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger(1);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("所有玩家进入下" + a.incrementAndGet() +"关！"));

        for (int i = 1; i <= 4; i++) {
            new Thread(new Player(i, cyclicBarrier)).start();
        }
    }
}

/**
 * 玩家类
 *
 * @author itmyhome
 */
class Player implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private int id;

    public Player(int id, CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            System.out.println("玩家" + id + "正在玩第 1 关...");
            cyclicBarrier.await();
            System.out.println("玩家" + id + "进入第 2 关...");
            cyclicBarrier.await();
            System.out.println("玩家" + id + "进入第 3 关...");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
