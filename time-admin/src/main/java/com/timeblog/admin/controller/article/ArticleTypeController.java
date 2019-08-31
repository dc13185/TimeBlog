package com.timeblog.admin.controller.article;

import com.github.pagehelper.PageHelper;
import com.timeblog.business.base.BaseController;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.ArticleType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import timeblog.framework.mapper.ArticleTypeMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: dong.chao
 * @create: 2019-07-29 20:20
 * @description: 文章类型
 **/
@Controller
@RequestMapping("/articleType")
public class ArticleTypeController extends BaseController {

    @Resource
    private ArticleTypeMapper articleTypeMapper;

    @RequestMapping("/toArticleType")
    public String toArticleType(){
        return "article/articleType";
    }


    @RequestMapping("/showAticleType")
    @ResponseBody
    public Result showArticleType(){
        PageHelper.startPage(1,10);
        List<ArticleType> articleTypeList =  articleTypeMapper.queryAll(new ArticleType());
        return null;
    }
}
