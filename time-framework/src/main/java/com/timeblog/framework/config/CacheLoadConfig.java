package com.timeblog.framework.config;

import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.Sentence;
import com.timeblog.framework.mapper.*;
import com.timeblog.framework.system.constant.SpiderConstant;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.RedisUtils;
import com.timeblog.framework.task.ScheduledTask;
import com.timeblog.framework.task.SchedulingRunnable;
import com.timeblog.framework.task.config.CronTaskRegistrar;
import com.timeblog.framework.task.entity.TimeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Dong.Chao
 * @Classname CacheLoadConfig
 * @Description 启动加载缓存
 * @Date 2020/2/27 22:38
 * @Version V1.0
 */
@Component
public class CacheLoadConfig {

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private ArticleTypeMapper articleTypeMapper;

    @Resource
    private BlogWebConfigDao blogWebConfigDao;

    @Autowired
    private SentenceDao sentenceDao;

    @Autowired
    private TimeTaskDao timeTaskDao;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;


    @PostConstruct
    public void init() throws Exception {
        //分类
        List<ArticleType> articleTypes = articleTypeMapper.queryAllTimeArticleTypes();
        redisUtils.set(SystemConstant.TEMP_ARTICLE_TYPES,articleTypes);
        //配置
        BlogWebConfig blogWebConfig = blogWebConfigDao.queryAll();
        redisUtils.set(SystemConstant.WEB_BLOG_CONFIG,blogWebConfig);
        SystemConstant.BLOGWEBCONFIG = blogWebConfig;
        //句子
        List<Sentence> sentences = sentenceDao.queryByDate(LocalDate.now().toString());
        SpiderConstant.SENTENCES = sentences;
        //定时调度任务
        List<TimeTask> timeTasks =  timeTaskDao.queryAll(TimeTask.builder().status(1).build());
        timeTasks.forEach(t ->{
            SchedulingRunnable task = new SchedulingRunnable(t.getTaskBeanName(), t.getTaskMethod(), null);
            //加入任务
            cronTaskRegistrar.addCronTask(task, t.getTaskCron());
        });
    }
}
