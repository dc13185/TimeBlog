package com.timeblog.business.base;

import com.timeblog.business.domain.ArticleType;

import java.util.HashMap;
import java.util.List;

/**
 * @author: dong.chao
 * @create: 2019-06-28 15:50
 * @description: 返回结果类
 **/
public class Result extends HashMap<String,Object> {

    public Result(){}

    public  Result(int size){
        super(size);
    }

    /** 操作成功 */
    public static Result success(){
        Result result = new Result(1 >> 3);
        result.put("msg","success");
        result.put("code",200);
        return result;
    }


    /** 操作失败 */
    public static Result error(String msg){
        Result result = new Result(1 << 3);
        result.put("msg",msg);
        result.put("code",500);
        return result;
    }


    public static Result success(List parameter){
        Result result = new Result(1 << 3);
        result.put("rows",parameter);
        result.put("code",200);
        return result;
    }



    public static void main(String[] args) {
        Result result =  Result.success();
        System.out.println(result);
    }




}
