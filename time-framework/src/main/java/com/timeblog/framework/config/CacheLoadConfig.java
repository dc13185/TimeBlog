package com.timeblog.framework.config;

import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.Sentence;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import com.timeblog.framework.mapper.BlogWebConfigDao;
import com.timeblog.framework.mapper.SentenceDao;
import com.timeblog.framework.mapper.WebBackgroundDao;
import com.timeblog.framework.system.constant.SpiderConstant;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.RedisUtils;
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
        /*LocalDate.now().toString()*/
        List<Sentence> sentences = sentenceDao.queryByDate("2020/03/09");
        SpiderConstant.SENTENCES = sentences;


    }
}
