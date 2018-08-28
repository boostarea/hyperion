package com.ooooor.basic.escapeAnalysis;

/**
 * 逃逸分析： 观察某一变量是否会逃出作用域，必须在-server模式下
 *
 * 锁消除基于逃逸分析，在非并发下，并发类（StringBuffer、Vector等）会去除无用锁操作
 *
 */
public class EscapeAnalysisDemo {

}
