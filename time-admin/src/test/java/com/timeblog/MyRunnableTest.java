package com.timeblog;

/**
 * @author: dong.chao
 * @create: 2019-06-20 19:55
 * @description: ceshi
 **/
public class MyRunnableTest implements Runnable {

    private int i;

    synchronized   void print() {
        System.out.println(Thread.currentThread().getName() + " " + i);
    }

    public void run() {
        for (; i < 100; i++) {
            // 当线程类实现Runnable接口时，
            // 如果想获取当前线程，只能用Thread.currentThread()方法。
            print();
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "" + i);
            if (i == 20) {
                MyRunnableTest st = new MyRunnableTest();
                // 通过new Thread(target , name)方法创建新线程
                new Thread(st, "新线程-1").start();
                new Thread(st, "新线程-2").start();
            }
        }
    }
}