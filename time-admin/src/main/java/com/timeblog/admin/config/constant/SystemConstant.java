package com.timeblog.admin.config.constant;

import java.util.regex.Pattern;

/**
 * @author Lmm
 * @Classname SystemConstant
 * @Description 系统常量配置
 * @Date 2020/2/14 20:28
 * @Version V1.0
 */
public class SystemConstant {


    public static String SOURCE_IMG = "http";

    /**
     * 不启用CSRL的URL
     */
    public static String CRSF_CLOSE_URL = "/article/uploadImage/**";

    /**
     * 临时草稿的Key
     */
    public static String TEMP_ARTICLE_FLAG = "tempArticle";

    /**
     *  临时草稿的Key
     */
    public static String TEMP_ARTICLE_IMAGES_FLAG = "tempArticleImages";


    /**
     *  临时草稿的Key
     */
    public static String TEMP_ARTICLE_TYPES = "tempArticleTypes";

    /**
     *  文章标签
     */
    public static String ARTICLE_LABEL_FLAG = "articleLabelFlag";

    /**
     *  文章对应的类型
     */
    public static String ARTICLE_TO_LABEL_FLAG = "articleToLabelFlag";

    /**
     *  获取图片文件路径正则
     */
    public static Pattern IMAGE_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}[\\s\\S]{1,}");


    //todo  更换主题设计   一个Key对应一个主题






}
