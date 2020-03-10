package com.timeblog.admin.controller.task;

import com.timeblog.business.base.Result;
import com.timeblog.framework.mapper.TimeTaskDao;
import com.timeblog.framework.task.SchedulingRunnable;
import com.timeblog.framework.task.config.CronTaskRegistrar;
import com.timeblog.framework.task.entity.TimeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dong.Chao
 * @Classname TaskController
 * @Description 定时任务配置
 * @Date 2020/3/10 12:37
 * @Version V1.0
 */

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TimeTaskDao timeTaskDao;

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @RequestMapping("/toTaskList")
    public String toTaskList(){
        return "config/taskList";
    }


    /**
     * @author: dongchao
     * @create: 2020/3/10-15:44
     * @description:查询所有任务类
     * @param:
     * @return:
     */
    @RequestMapping("/queryAllTasks")
    @ResponseBody
    public Result queryAllTasks(){
       List<TimeTask> timeTasks = timeTaskDao.queryAll(null);
       return Result.success(timeTasks);
    }


    /**
     * @author: dongchao
     * @create: 2020/3/10-16:19
     * @description:入库定时调度数据
     * @param:
     * @return:
     */
    @RequestMapping("/saveTask")
    @ResponseBody
    public Result saveTask(@RequestBody  TimeTask timeTask){
        timeTask.setStatus(0);
        timeTaskDao.insert(timeTask);
        return Result.success();
    }


    /**
     * @author: dongchao
     * @create: 2020/3/10-16:19
     * @description:修改cron表达式
     * @param:
     * @return:
     */
    @RequestMapping("/updateCron")
    @ResponseBody
    public Result updateCron(@RequestBody  TimeTask srcTimeTask){
        TimeTask timeTask = timeTaskDao.queryById(srcTimeTask.getTaskId());
        if (timeTask.getStatus() == 1){
            SchedulingRunnable task = new SchedulingRunnable(timeTask.getTaskBeanName(), timeTask.getTaskMethod(), null);
            cronTaskRegistrar.addCronTask(task, srcTimeTask.getTaskCron());
        }

        timeTaskDao.update(srcTimeTask);
        return Result.success();
    }



    /**
     * @author: dongchao
     * @create: 2020/3/10-16:19
     * @description:启动/关闭定时任务
     * @param:
     * @return:
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Result startUpTaks(@RequestBody TimeTask srcTimeTask){
        TimeTask timeTask = timeTaskDao.queryById(srcTimeTask.getTaskId());
        SchedulingRunnable task = new SchedulingRunnable(timeTask.getTaskBeanName(), timeTask.getTaskMethod(), null);
        TimeTask updateTask = timeTask.toBuilder().taskId(timeTask.getTaskId()).build();
        if (timeTask.getStatus() == 0){
            updateTask.setStatus(1);
            //加入任务
            cronTaskRegistrar.addCronTask(task, timeTask.getTaskCron());
        }else{
            updateTask.setStatus(0);
            cronTaskRegistrar.removeCronTask(task);
        }

        timeTaskDao.update(updateTask);

        return Result.success();
    }


    /**
     * @author: dongchao
     * @create: 2020/3/10-16:19
     * @description:定时任务执行一次
     * @param:
     * @return:
     */
    @RequestMapping("/executeTaks")
    @ResponseBody
    public Result executeTaks(@RequestBody TimeTask srcTimeTask){
        TimeTask timeTask = timeTaskDao.queryById(srcTimeTask.getTaskId());
        SchedulingRunnable task = new SchedulingRunnable(timeTask.getTaskBeanName(), timeTask.getTaskMethod(), null);
        LocalDateTime startTime = LocalDateTime.now();
        task.run();
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime,endTime);
        //相差毫秒数
        long mills =  duration.toMillis();
        return Result.success().add("mills",mills);
    }





}
