package com.timeblog.web.controller;

import com.timeblog.business.base.Result;
import com.timeblog.business.domain.Article;
import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.PageDomain;
import com.timeblog.framework.mapper.ArticleMapper;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dong.Chao
 * @Classname WebArticleController
 * @Description
 * @Date 2020/2/28 16:40
 * @Version V1.0
 */
@Controller
@RequestMapping("/web/article")
public class WebArticleController {

    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private ArticleMapper articleMapper;



    @RequestMapping("/toArticles")
    public ModelAndView toArticles(){
        ModelAndView modelAndView = new ModelAndView("web/article");
        //查找首级分类
        List<ArticleType> articleTypeList =  (List<ArticleType>) redisUtils.get(SystemConstant.TEMP_ARTICLE_TYPES);
        articleTypeList =  articleTypeList.stream().filter(l -> "-1".equals(l.getParentId())).collect(Collectors.toList());
        BlogWebConfig blogWebConfig = (BlogWebConfig)redisUtils.get(SystemConstant.WEB_BLOG_CONFIG);

        modelAndView.addObject("articleTypeList",articleTypeList)
                    .addObject("blogWebConfig",blogWebConfig);
        return modelAndView;
    }

    @RequestMapping("/showArticleList")
    @ResponseBody
    public Result showArticleListWithPage(@RequestBody PageDomain pageDomain){
        //查询所有已经发布了的
        List<Article> articleTypeList =  articleMapper.queryAllFormal(pageDomain);
        return Result.success(articleTypeList);
    }


}
