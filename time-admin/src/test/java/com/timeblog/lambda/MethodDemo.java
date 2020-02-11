package com.timeblog.lambda;

import java.util.*;
import java.util.function.Function;

/**
 * @author: dong.chao
 * @create: 2019-09-04 20:29
 * @description: 方法引用测试  、  资源
 **/
public class MethodDemo {

    public static void main(String[] args) {
        MethodDemo methodDemo = new MethodDemo();
        System.out.println(methodDemo.comput(2,value-> 2 * value));
        int z = methodDemo.comput(5,value-> {
            int sum= 1;
            for (Integer i = 1; i < value; i++) {
                sum *= i;
            }
            return sum;
        });
        System.out.println(z);
    }

    public int comput(int a, Function<Integer,Integer> function){
        return function.apply(a);
    }
}
