package com.timeblog;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: dong.chao
 * @create: 2019-06-20 19:03
 * @description: callable测试
 **/
public class MyCallable  extends Thread {

    private int i;

    @Override
    public void run() {
        for (; i < 100; i++) {
            // 当线程类继承Thread类时，直接使用this即可获取当前线程
            // Thread对象的getName()返回当前该线程的名字
            // 因此可以直接调用getName()方法返回当前线程的名
            System.out.println(getName() + "" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // 调用Thread的currentThread方法获取当前线程
            System.out.println(Thread.currentThread().getName() + "" + i);
            if (i == 20) {
                // 创建、并启动第一条线程
                Thread thread1 =  new MyCallable();
                thread1.setName("Thread1");
                thread1.start();
                // 创建、并启动第二条线程
                Thread thread2 = new MyCallable();
                thread2.setName("Thread2");
                thread2.start();


            }
        }
    }
}
