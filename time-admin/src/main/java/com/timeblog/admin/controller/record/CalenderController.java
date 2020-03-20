package com.timeblog.admin.controller.record;

import com.timeblog.business.base.Result;
import com.timeblog.business.domain.TimeRecord;
import com.timeblog.business.domain.dto.QueryRecordDto;
import com.timeblog.framework.mapper.TimeRecordDao;
import com.timeblog.framework.system.utils.CronUtils;
import com.timeblog.framework.system.utils.DateUtils;
import com.timeblog.framework.system.utils.MailUtil;
import com.timeblog.framework.task.SchedulingRunnable;
import com.timeblog.framework.task.config.CronTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        //如果相等，则证明没有选择结束时间,往后延迟23个小时
        if (timeRecord.getRecordStartTime().equals(timeRecord.getRecordEndTime())){
            LocalDateTime localDateTime = DateUtils.date2LocalDateTime(timeRecord.getRecordEndTime());
            localDateTime = localDateTime.plusHours(23);
            timeRecord.setRecordEndTime(DateUtils.localDateTime2Date(localDateTime));
        }
        timeRecordDao.insert(timeRecord);

        //小于-1 的为代办事件，定时邮件通知
        if (-1 >= timeRecord.getEventType()){
            String cron = CronUtils.getCron(timeRecord.getRecordStartTime());
            System.out.println("插入时间的入参hashCode"+timeRecord.hashCode());
            SchedulingRunnable task = new SchedulingRunnable("mailUtil", "sendMailForMeByRecord", timeRecord);
            System.out.println(task.hashCode());
            cronTaskRegistrar.addCronTask(task, cron);
        }
        return Result.success();
    }



    @RequestMapping("/deleteRecordById")
    @ResponseBody
    public Result deleteRecordById(@RequestBody TimeRecord timeRecord){
        //如果为待办事项，删除定时任务
        if (timeRecord.getStatus() <= -1){
            TimeRecord todoRecord = timeRecordDao.queryById(timeRecord.getRecordId());
            mailUtil.removeMailScheduled(todoRecord);
        }
        timeRecordDao.deleteById(timeRecord.getRecordId());
        return Result.success();
    }

    @RequestMapping("/completeRecordById")
    @ResponseBody
    public Result completeRecordById(@RequestBody TimeRecord timeRecord){
        //如果为待办事项，删除定时任务
        if (timeRecord.getStatus() <= -1){
            TimeRecord todoRecord = timeRecordDao.queryById(timeRecord.getRecordId());
            mailUtil.removeMailScheduled(todoRecord);
        }
        timeRecord.setStatus(1);
        timeRecordDao.update(timeRecord);
        return Result.success();
    }





}
