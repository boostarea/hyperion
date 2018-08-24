package com.company.Wait;

/**
 * 1. wait/notify/notifyAll 包含在synchronized代码块内,获取对象监视器
 *      (1. wait不会平白无故发生,需要条件,条件可能被其他线程修改;
 *       2. wait/notify涉及线程间通信,保证原子性;)
 * 2. notify()唤醒等待队列, 不释放锁,随机不公平唤醒 ?
 * 3. wait()后等待并释放锁,sleep()不释放任何资源
 * 4. wait()被唤醒,还需等待重新获得锁,才能执行
 *
 */
public class WaitAndNotify {
    private final static Object object = new Object();

    /**
     * test for : wait()被唤醒,还需等待重新获得锁,才能执行
     * output:
     * 1535008282137 T1 start
     * 1535008282137 T1 wait
     * 1535008282139 T2 notify
     * 1535008282139 T2 end
     * 1535008284139 T1 end
     */
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + " T1 start");
                try {
                    System.out.println(System.currentTimeMillis() + " T1 wait");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + " T1 end");
            }
        }).start();

        new Thread(() -> {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + " T2 notify");
                object.notify();
                System.out.println(System.currentTimeMillis() + " T2 end");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
