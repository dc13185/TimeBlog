package com.timeblog.web.controller;

import com.google.common.collect.Lists;
import com.timeblog.business.domain.Article;
import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.Sentence;
import com.timeblog.framework.mapper.ArticleMapper;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import com.timeblog.framework.system.constant.SpiderConstant;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dong.Chao
 * @Classname WebIndexController
 * @Description 首页
 * @Date 2020/2/26 10:46
 * @Version V1.0
 */

@Controller
@RequestMapping("/web/index")
public class WebIndexController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ArticleTypeMapper articleTypeMapper;


    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("/toIndex")
    public ModelAndView toIndex(){
        //获取博客设置
        Integer topArticleTypeId = SystemConstant.BLOGWEBCONFIG.getIndexTopArticleType();

        ArticleType articleType = new ArticleType();
        List<Article> articles = Lists.newArrayList();
        if (topArticleTypeId != null){
            List<ArticleType> articleTypes = (List<ArticleType>)redisUtils.get(SystemConstant.TEMP_ARTICLE_TYPES);
            articleType = articleTypes.stream().filter(l -> l.getTypeId().equals(topArticleTypeId.toString())).findAny().orElse(new ArticleType());
            articles = articleMapper.queryAllByTypeAndLimit(topArticleTypeId,0,3);
        }
        Sentence sentence = SpiderConstant.getRandomSentence();
        String footerSentence = SpiderConstant.getRandomSentence().getContent();

        ModelAndView modelAndView = new ModelAndView("web/index");
        modelAndView
                .addObject("blogWebConfig",SystemConstant.BLOGWEBCONFIG)
                .addObject("articleType",articleType)
                .addObject("articles",articles)
                .addObject("sentence",sentence)
                .addObject("footerSentence",footerSentence);;
        return modelAndView;
    }






}
