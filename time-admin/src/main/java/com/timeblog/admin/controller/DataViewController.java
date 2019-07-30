package com.timeblog.admin.controller;

import com.timeblog.business.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: dong.chao
 * @create: 2019-07-27 16:52
 * @description: 数据初始化
 **/
@Controller
@RequestMapping("/dataView")
public class DataViewController extends BaseController {

    /** 
    * @Description: 初始化首页数据
    * @Param: [] 
    * @return: java.lang.String 
    * @Author: dong.chao
    * @Date: 2019/7/27 
    */
    @RequestMapping("/toDataIndex")
    public String toDataIndex(){
        return "index/index_v1";
    }

}
