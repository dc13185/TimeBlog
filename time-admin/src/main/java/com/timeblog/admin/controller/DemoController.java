package com.timeblog.admin.controller;

import com.timeblog.admin.mapper.DemoMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: dong.chao
 * @create: 2019-06-17 16:05
 * @description: ceshi
 **/

@RestController
@RequestMapping("/demo1")
public class DemoController {

    @Resource
    private DemoMapper demoMapper;

    @RequestMapping("/test")
    public String test(){
        demoMapper.addDemoInfo();
        return"ok";
    }

}
