package com.timeblog.admin.controller;

import com.timeblog.admin.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dong.chao
 * @create: 2019-06-17 16:05
 * @description: ceshi
 **/

@RestController
@RequestMapping("/demo1")
public class DemoController {

    @Autowired
    private DemoMapper demoMapper;

    @RequestMapping("/test")
    public String test(){
        demoMapper.addDemoInfo();
        return"ok";
    }

}
