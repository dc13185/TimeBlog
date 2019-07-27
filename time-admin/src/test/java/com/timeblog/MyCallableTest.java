package com.timeblog;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author: dong.chao
 * @create: 2019-06-20 20:03
 * @description: callbale
 **/
public class MyCallableTest implements Callable<Integer> {
    public Integer call() throws Exception {
        int i = 0;
        for ( ; i < 100 ; i++ ){
            System.out.println(Thread.currentThread().getName()+ "\t" + i);
        }
        // call()方法可以有返回值
        return i;
    }

    public static void main(String[] args) {
        // 创建Callable对象
        MyCallableTest myCallableTest = new MyCallableTest();
        // 使用FutureTask来包装Callable对象
        FutureTask<Integer> task = new FutureTask<Integer>(myCallableTest);
        for (int i = 0 ; i < 100 ; i++){
            System.out.println(Thread.currentThread().getName()+ " \t" + i);
            if (i == 20){
                // 实质还是以Callable对象来创建、并启动线程
                new Thread(task , "callable").start();
            }
        }
        try{
            // 获取线程返回值
            System.out.println("callable返回值：" + task.get());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
