package com.timeblog.admin.controller.record;

import com.timeblog.business.base.Result;
import com.timeblog.business.domain.TimeRecord;
import com.timeblog.business.domain.dto.QueryRecordDto;
import com.timeblog.framework.mapper.TimeRecordDao;
import com.timeblog.framework.system.utils.CronUtils;
import com.timeblog.framework.system.utils.MailUtil;
import com.timeblog.framework.task.SchedulingRunnable;
import com.timeblog.framework.task.config.CronTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

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

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    MailUtil mailUtil;


    @RequestMapping("/toRecord")
    public String toRecord(){
        return "recording/calendar";
    }


    @RequestMapping("/queryRecordByTime")
    @ResponseBody
    public Result queryRecordByTime(@RequestBody QueryRecordDto queryRecordDto){
        //开始时间，结束时间
        List<TimeRecord> timeRecords = timeRecordDao.queryAllByDate(queryRecordDto.getStartDate(),queryRecordDto.getEndDate());
        return Result.success(timeRecords);


    }




    @RequestMapping("/insertRecordByTime")
    @ResponseBody
    public Result insertRecordByTime(@RequestBody TimeRecord timeRecord){
        //小于-1 的为代办事件，定时邮件通知
        if (-1 >= timeRecord.getEventType()){
            String cron = CronUtils.getCron(timeRecord.getRecordStartTime());
            System.out.println(cron);
            SchedulingRunnable task = new SchedulingRunnable("mailUtil", "sendMailForMeByRecord", timeRecord);
            TimeRecord timeRecord1 = timeRecord.toBuilder().build();
            System.out.println(timeRecord.equals(timeRecord1));

            //加入任务 ss mm HH dd MM ?
            cronTaskRegistrar.addCronTask(task, cron);



            SchedulingRunnable task2 = new SchedulingRunnable("mailUtil", "sendMailForMeByRecord", timeRecord1);
            System.out.println(task.hashCode());
            System.out.println(task2.hashCode());
            System.out.println(task.equals(task2));


            cronTaskRegistrar.removeCronTask(task2);
        }
        timeRecordDao.insert(timeRecord);
        return Result.success();
    }






}
