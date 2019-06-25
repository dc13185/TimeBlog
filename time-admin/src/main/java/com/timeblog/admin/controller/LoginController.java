package com.timeblog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: dong.chao
 * @create: 2019-06-25 19:27
 * @description: 登录Controller
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
    * @Description:   前往登录页面
    * @Param: []
    * @return: java.lang.String
    * @Author: dong.chao
    * @Date: 2019/6/25
    */
    @RequestMapping("/toLoginView")
    public String toLoginView(){
        return  "login";
    }



}
