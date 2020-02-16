package com.timeblog.admin.controller.article;

import com.timeblog.business.base.BaseController;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.PageDomain;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping("/saveArticleType")
    @ResponseBody
    public Result saveArticleType(@RequestBody ArticleType articleType) throws Exception {
        //为空报异常
        Optional.ofNullable(articleType).orElseThrow(()->new Exception("文章实例为空"));
        Optional.ofNullable(articleType).ifPresent(at ->{
            if (at.getIsAvailable() == null){
                //默认可用
                at.setIsAvailable(0);
            }
            //序列先不做
            at.setSort(0);
        });
        articleTypeMapper.insert(articleType);
        return Result.success();
    }


    @RequestMapping("/editArticleType")
    @ResponseBody
    public Result editArticleType(@RequestBody ArticleType articleType){
        try {
            articleTypeMapper.update(articleType);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error(e.getMessage());
        }
        return Result.success();
    }


    @RequestMapping("/deleteArticleType")
    @ResponseBody
    public Result deleteArticleType(@RequestBody HashMap<String,String> map){
        String typeIds = map.get("typeIds");
        List<String> typeIdList = Arrays.asList(typeIds.split(","));
        articleTypeMapper.deleteById(typeIdList);
        return Result.success();
    }


}
