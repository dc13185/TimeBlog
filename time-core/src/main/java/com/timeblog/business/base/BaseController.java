package com.timeblog.business.base;

/**
 * @author: dong.chao
 * @create: 2019-06-28 15:49
 * @description: 父级Controller
 **/
public class BaseController {


    /** 成功消息 */
    protected Result success(){
        return Result.success();
    }



    /** 失败消息 */
    protected Result error(String msg){
        return Result.error(msg);
    }

}