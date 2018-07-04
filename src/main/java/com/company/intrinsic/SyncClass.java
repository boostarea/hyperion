package com.company.intrinsic;

/**
 * 线程安全诱因:
 *  1.存在共享数据(临界数据); synchronized 可代替volatile
 *  2.多条线程共同操作共享数据; 互斥锁 :保证同一时刻有且只有一个线程在操作共享数据，其他线程必须等到该线程处理完数据后再进行
 *
 *
 * 毕竟一个对象只有一把锁，当一个线程获取了该对象的锁之后，其他线程无法获取该对象的锁，所以无法访问该对象的其他synchronized实例方法,但是其他线程还是可以访问该实例对象的其他非synchronized方法
 *
 * 对象头 Mark Word
 * synchronized关键字不能继承
 *
 * 使用monitor对象为显式同步,通过字节码指令控制;
 * 监视器锁（monitor）是依赖于底层的操作系统的Mutex Lock来实现的;
 * 操作系统实现线程之间的切换时需要从用户态转换到核心态，这个状态之间的转换需要相对比较长的时间，时间成本相对较高
 * 在Java 6之后，为了减少获得锁和释放锁所带来的性能消耗，引入了轻量级锁和偏向锁
 *
 * 锁的状态总共有四种，无锁状态、偏向锁、轻量级锁和重量级锁
 * 锁的升级是单向的，也就是说只能从低到高升级，不会出现锁的降级(在safepoint可能出现降级 Deflation)
 *
 * synchronized方法(隐式) 并不是由 monitorenter 和 monitorexit 指令来实现同步的，而是由方法调用指令读取运行时常量池中方法表结构(method_info Structure) 中的 ACC_SYNCHRONIZED 标志来隐式实现的
 *
 * 同步语句块(显式) 的实现使用的是monitorenter 和 monitorexit 指令
 *
 *
 *对象: 对象头/实例变量/填充数据
 */
public class SyncClass implements Runnable {

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
        Thread t1 = new Thread(new SyncClass());
        Thread t2 = new Thread(new SyncClass());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(i);
    }
}
