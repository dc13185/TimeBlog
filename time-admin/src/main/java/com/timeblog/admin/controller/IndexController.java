package com.timeblog.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: dong.chao
 * @create: 2019-06-25 19:27
 * @description: 首页
 **/

@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/toIndexView")
    public String toIndexView(){
        return  "index";
    }


}
