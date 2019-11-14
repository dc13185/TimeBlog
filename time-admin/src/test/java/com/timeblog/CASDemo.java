package com.timeblog;
import	java.util.HashMap;

/**
 * @author: dong.chao
 * @create: 2019-09-02 09:46
 * @description: CAS 测试类
 **/
public class CASDemo {

    public static void main(String[] args){

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer> ();
        for (int i = 0; i <10000 ; i++) {
            map.put(i,i);
        }

    }
}
