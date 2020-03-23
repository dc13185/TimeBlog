package com.timeblog.framework.config;

import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.Sentence;
import com.timeblog.business.domain.TimeRecord;
import com.timeblog.business.domain.dto.StatisticsDto;
import com.timeblog.business.domain.vo.StatisticsVo;
import com.timeblog.framework.mapper.*;
import com.timeblog.framework.system.constant.SpiderConstant;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.CronUtils;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dong.Chao
 * @Classname CacheLoadConfig
 * @Description 启动加载缓存
 * @Date 2020/2/27 22:38
 * @Version V1.0
 */
@Component
public class CacheLoadConfig {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ArticleTypeMapper articleTypeMapper;

    @Resource
    private BlogWebConfigDao blogWebConfigDao;

    @Resource
    private SentenceDao sentenceDao;

    @Resource
    private TimeTaskDao timeTaskDao;

    @Resource
    private TimeRecordDao timeRecordDao;

    @Resource
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

        //查询未完成事件 加入调度任务
        List<TimeRecord> recordList =  timeRecordDao.queryTodoRecord(new Date());
        recordList.forEach(timeRecord -> {
            String cron = CronUtils.getCron(timeRecord.getRecordStartTime());
            SchedulingRunnable task = new SchedulingRunnable("mailUtil" ,"sendMailForMeByRecord", timeRecord);
            //加入任务
            cronTaskRegistrar.addCronTask(task, cron);
        } );


        //相关统计
        List<StatisticsDto> statisticsDtoList =  timeRecordDao.statisticsData(null);
        //加载数据
        for (TimeRecord.eventEnum value : TimeRecord.eventEnum.values()) {
            loadStatisticsData(statisticsDtoList,value.getEventType(),value.getEventTitle());
        }


        System.out.println(1);
    }


    /**
     * @author: dongchao
     * @create: 2020/3/22-0:27
     * @description: 加载数据到缓存
     * @param:
     * @return:
     */
    private  void loadStatisticsData(List<StatisticsDto> statisticsDtoList ,  int eventType,String eventName){

        List<StatisticsDto> eventStatistics = statisticsDtoList.stream().filter(sd-> sd.getEventType().equals(String.valueOf(eventType))).collect(Collectors.toList());
        if (eventStatistics.size() > 0){
            //求出总天数
            Integer sumDays =  eventStatistics.stream().mapToInt(StatisticsDto::getDays).sum();
            //最大连续天数
            StatisticsDto maxStatisticsDto = eventStatistics.stream().max(Comparator.comparingInt(StatisticsDto::getDays)).get();
            //相关数据加载到缓存
            StatisticsVo statisticsVo = StatisticsVo.builder().totalNumber(sumDays)
                    .maxConsecutiveNumber(maxStatisticsDto.getDays())
                    .maxEndDate(maxStatisticsDto.getEndDate())
                    .maxStartDate(maxStatisticsDto.getStartDate())
                    .eventTypeName(eventName).build();
            SystemConstant.STATISTICS_VO_LIST.add(statisticsVo);

        }
    }
}
