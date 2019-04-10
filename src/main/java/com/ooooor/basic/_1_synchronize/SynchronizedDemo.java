package com.ooooor.basic._1_synchronize;

/**
 * 一个对象只有一把锁，当一个线程获取了该对象的锁之后，其他线程无法获取该对象的锁，所以无法访问该对象的其他synchronized实例方法,但是其他线程还是可以访问该实例对象的其他非synchronized方法
 * synchronized关键字不能继承
 *
 * 对象头 Mark Word
 *
 * synchronized方法(隐式) 并不是由 monitorenter 和 monitorexit 指令来实现同步的，而是由方法调用指令读取运行时常量池中方法表结构(method_info Structure) 中的 ACC_SYNCHRONIZED 标志来隐式实现的
 * 同步语句块(显式) 的实现使用的是monitorenter 和 monitorexit 指令
 *
 *对象: 对象头/实例变量/填充数据
 */
public class SynchronizedDemo implements Runnable {

    static int i = 0;

    /**
     * 类锁
     */
    public static synchronized void increase() {
        i++;
    }

    /**
     * 对象锁,类锁不发生互斥;产生线程安全
     */
    public synchronized void increse4Obj() {
        i++;
    }

    public void run() {
        for (int j=0; j<100000; j++) {
            increase();
        }
    }

    /**
     * 1. 对象锁 (修饰实例方法/修饰代码块) 重量级锁指向monitor对象,位于对象头
     * 2. 类锁 (修饰静态方法/修饰代码块)
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SynchronizedDemo());
        Thread t2 = new Thread(new SynchronizedDemo());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(i);
    }
}
