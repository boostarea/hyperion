package com.ooooor.concurrent.SkipListMap;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 1. 跳表(部分锁)类似平衡树(全局锁),但插入/删除不需要全局调整,对node实行cas操作;
 * 2. 随机算法,维护多层链表,最底层为所有元素,且都为有序排列;
 * 3. 适用于有序性需求;
 *
 *
 */
public class SkipListMapDemo {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> skipMapper = new ConcurrentSkipListMap<>();

        for (int i=30; i>0; i--) {
            skipMapper.put(i, i);
        }

        for (Integer k : skipMapper.keySet()) {
            System.out.println(skipMapper.get(k));
        }
    }
}
