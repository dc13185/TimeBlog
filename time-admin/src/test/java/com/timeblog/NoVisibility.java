package com.timeblog;

/**
 * @author: dong.chao
 * @create: 2019-06-24 20:26
 * @description: 重排序不安全问题
 **/
public class NoVisibility {
    private static volatile boolean ready;
    private static volatile int number;

    public static  class ReaderThread extends  Thread{
        @Override
        public void run() {
           while(!ready){
               Thread.yield();
               System.out.println(number);
           }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(100);
        number = 42;
        ready = true;
    }
}
