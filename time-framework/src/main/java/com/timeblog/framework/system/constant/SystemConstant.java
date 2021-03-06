package com.timeblog.framework.system.constant;

import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.TimeRecord;
import com.timeblog.business.domain.vo.StatisticsVo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author Lmm
 * @Classname SystemConstant
 * @Description 系统常量配置
 * @Date 2020/2/14 20:28
 * @Version V1.0
 */
public class SystemConstant {


    public static final String SOURCE_IMG = "http";

    public static final String BLANK_IMG = "img-blank";

    /**
     * 不启用CSRL的URL
     */
    public static  String CRSF_CLOSE_URL = "/article/uploadImage/**";

    /**
     * 临时草稿的Key
     */
    public static final String TEMP_ARTICLE_FLAG = "tempArticle";

    /**
     *  文章图片的Key
     */
    public static final String TEMP_ARTICLE_IMAGES_FLAG = "tempArticleImages";


    /**
     *  文章所有类型Key
     */
    public static final String TEMP_ARTICLE_TYPES = "tempArticleTypes";

    /**
     *  文章标签
     */
    public static final String ARTICLE_LABEL_FLAG = "articleLabelFlag";

    /**
     *  文章对应的标签的KEy
     */
    public static final String ARTICLE_TO_LABEL_FLAG = "articleToLabelFlag";

    /**
     *  获取图片文件路径正则
     */
    public static final Pattern IMAGE_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}[\\s\\S]{1,}");


    /**
     *  博客相应配置的Key
     */
    public static final String WEB_BLOG_CONFIG = "webBlogConfig";


    /**
     *  博客相应句子的Key
     */
    public static final String WEB_BLOG_SENTENCE = "webBlogSentence";

    /**
     *  博客博文访问量的Key
     */
    public static final String WEB_BLOG_ARTICLE_ACCESS_COUNT = "webBlogArticleAccessCount";

    /**
     *  博客博文访问量的Key
     */
    public static final String WEB_BLOG_ARTICLE_LIKE_COUNT = "webBlogArticleLikeCount";

    /**
     *  博客系统设置
     */
    public static  BlogWebConfig BLOGWEBCONFIG;


    /**
     *  相关统计数据
     */
    public static  final List<StatisticsVo> STATISTICS_VO_LIST = new ArrayList<>();


    //todo  更换主题设计   一个Key对应一个主题








}
