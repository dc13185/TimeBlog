package com.timeblog.mainshi;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;


/**
 * @author: dong.chao
 * @create: 2019-12-18 11:15
 * @description:
 **/
public class Test {
    @org.junit.Test
    public void test() throws Exception {
        /*int [] i = {1,5,3,30,28,4,8,9,0,90,40};
        insertSort(i);*/
        FatherDemo s = new SonDemo();
    }

    public void insertSort(int[] array){
        //从第一位开始
        for(int i=1;i<array.length;i++){
            int temp = array[i];
            //定义一个新的，以确定位置
            int j = i;
            while(j>0 && temp<array[j-1]){
                array[j] = array[--j];
            }
            if(i!=j){
                //替换
                array[j] = temp;
            }
        }
      //  System.out.println(Arrays.toString(array));
    }

}
