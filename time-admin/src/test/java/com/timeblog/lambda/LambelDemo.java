package com.timeblog.lambda;

import org.junit.Test;

public class LambelDemo {



    @Test
    public void test(){
        //无类型的
        MathOperation mathOperation = (a, b) -> a + b;
        //有类型的
        MathOperation addOperation = (int a, int b) -> a + b;
        //大括号返回的 减法
        MathOperation subtractionOperation = (int a, int b) ->{ return a - b; };

        GreetingService greetingService = (String message) -> System.out.println(message + "hahahah");

        System.out.println(subtractionOperation.operation(1,2));
        greetingService.sayMessage("aaa");

    }

}
