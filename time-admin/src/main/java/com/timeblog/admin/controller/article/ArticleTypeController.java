package com.timeblog.admin.controller.article;

import com.timeblog.business.base.BaseController;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.PageDomain;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping("/showArticleType")
    @ResponseBody
    public Result showArticleTypeWithPage(@RequestBody PageDomain pageDomain){
        List<ArticleType> articleTypeList =  articleTypeMapper.queryAll(pageDomain);
        return Result.success(articleTypeList);
    }
}
