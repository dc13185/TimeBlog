package com.timeblog.admin.controller.record;

import com.timeblog.framework.mapper.TimeRecordDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Dong.Chao
 * @Classname CalenderController
 * @Description 记录Controller
 * @Date 2020/3/17 15:54
 * @Version V1.0
 */

@Controller
@RequestMapping("/record")
public class CalenderController {

    @Resource
    private TimeRecordDao timeRecordDao;

    @RequestMapping("/toRecord")
    public String toRecord(){
        return "recording/calendar";
    }



}
