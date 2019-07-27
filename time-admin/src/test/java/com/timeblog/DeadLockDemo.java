package com.timeblog;

/**
 * @author: dong.chao
 * @create: 2019-06-20 17:38
 * @description: 死锁测试
 **/
public class DeadLockDemo {
    private static String A = "A";
    private static String B = "B";
    public static void main(String[] args) {
       new DeadLockDemo().deadLock();
    /*    System.out.println("1");*/
    }
    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                synchronized (A) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println("2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }



}
