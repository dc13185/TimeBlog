package com.timeblog.spilder.controller;

import com.timeblog.framework.task.SchedulingRunnable;
import com.timeblog.framework.task.config.CronTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dong.Chao
 * @Classname TestController
 * @Description 测试
 * @Date 2020/3/10 0:59
 * @Version V1.0
 */
@RequestMapping("/web/spider")
@Controller
public class TestController {


    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @RequestMapping("/test")
    public void testSpider() throws InterruptedException {

        SchedulingRunnable task = new SchedulingRunnable("sentenceSpiderController", "spider", null);
        task.run();

        //cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");

        // 便于观察
        //Thread.sleep(3000000);
    }


}
