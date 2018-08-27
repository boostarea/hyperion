package com.ooooor.basic.longIn32Bits;

/**
 * 32位JVM中，64位的Long读写不是原子
 *
 */
public class LongIn32Bits {
    public static long test = 0;

    public static class Modify implements Runnable {
        private long to;

        public Modify(long to) {
            this.to = to;
        }
        @Override
        public void run() {
            while(true) {
                LongIn32Bits.test = to;
                Thread.yield();
            }
        }
    }

    public static class Read implements Runnable {
        @Override
        public void run() {
            while(true) {
                long tmp = LongIn32Bits.test;
                if (tmp != 111L && tmp != -999L && tmp != 333L && tmp != -444L) {
                    System.out.println(tmp);
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Modify(111L)).start();
        new Thread(new Modify(-999L)).start();
        new Thread(new Modify(333L)).start();
        new Thread(new Modify(-444L)).start();
        new Thread(new Read()).start();
    }

}
