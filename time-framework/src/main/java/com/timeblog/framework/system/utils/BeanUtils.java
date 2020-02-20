package com.timeblog.framework.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.timeblog.business.domain.Article;

import java.util.Map;

/**
 * @author Dong.Chao
 * @Classname BeanUtils
 * @Description 对实体类的一些操作
 * @Date 2020/2/20 16:13
 * @Version V1.0
 */
public class BeanUtils {

    public static <T> T conversionToObject(Map source,Class target){
        return (T)JSONObject.toJavaObject((JSON)JSONObject.toJSON(source), target);
    }

    public static Map conversionToMap(Object source){
        return  JSON.parseObject(JSON.toJSONString(source), Map.class);
    }
}
