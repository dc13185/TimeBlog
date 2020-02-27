package com.timeblog.web.controller;

import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/toIndex")
    public ModelAndView toArticleList(){

        BlogWebConfig blogWebConfig = (BlogWebConfig)redisUtils.get(SystemConstant.WEB_BLOG_CONFIG);
        String sentence = "“我们一路奋战，不是为了改变世界，而是为了不让世界改变我们。”";
        ModelAndView modelAndView = new ModelAndView("web/index");
        modelAndView
                .addObject("blogWebConfig",blogWebConfig)
                .addObject("sentence",sentence);
        return modelAndView;
    }




}
