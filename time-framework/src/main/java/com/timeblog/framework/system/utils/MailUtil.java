package com.timeblog.framework.system.utils;

import com.timeblog.business.domain.TimeRecord;
import com.timeblog.framework.mapper.TimeRecordDao;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.task.SchedulingRunnable;
import com.timeblog.framework.task.config.CronTaskRegistrar;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Dong.Chao
 * @Classname MailUtil
 * @Description 邮箱工具类
 * @Date 2020/3/19 20:27
 * @Version V1.0
 */
@Component("mailUtil")
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Resource
    private TimeRecordDao timeRecordDao;


    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Value("${spring.mail.username}")
    private String sender;


    /**
     * 发送提示邮件给自己
     */
    public void sendMailForMe(String title,String content) {
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setFrom(sender);
        mimeMessage.setTo(SystemConstant.BLOGWEBCONFIG.getBlogAuthorMail());
        mimeMessage.setSubject(title);
        mimeMessage.setText(content);
        mailSender.send(mimeMessage);
    }




    /**
     * @author: dongchao
     * @create: 2020/3/19-22:14
     * @description: 待办事项发邮件提醒
     * @param:
     * @return:
     */
    public void sendMailForMeByRecord(TimeRecord record) {
        //发邮件
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setFrom(sender);
        mimeMessage.setTo(SystemConstant.BLOGWEBCONFIG.getBlogAuthorMail());
        mimeMessage.setSubject(record.getRecordTitle());
        mimeMessage.setText(record.getRecordContext());
        mailSender.send(mimeMessage);

        //定时调度完，从队列中删除
        SchedulingRunnable task = new SchedulingRunnable("mailUtil", "sendMailForMeByRecord", record);
        cronTaskRegistrar.removeCronTask(task);

    }
}
