package com.timeblog.framework.system.constant;

import com.timeblog.business.domain.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dong.Chao
 * @Classname SpiderConstant
 * @Description 抓取的全局变量
 * @Date 2020/3/9 16:08
 * @Version V1.0
 */
public class SpiderConstant {

    /**
     * 存放抓取的句子
     * */
    public static List<Sentence> SENTENCES = new ArrayList<>(1 << 8);


    /**
     * redis 句子key
     * */
    public static String REDIS_SENTENCES_FLAG = "redisSentencesFlag";


    /**
     * 随机获取一条句子
     * */
    public static Sentence getRandomSentence(){
        if (SENTENCES.size() > 0){
            return  SENTENCES.get(new Random().nextInt(SENTENCES.size()));
        }else{
            return Sentence.builder().author("孔枝泳").content("我们一路奋战，不是为了改变世界，而是为了不让世界改变我们。").works("熔炉").build();
        }
    }




}
