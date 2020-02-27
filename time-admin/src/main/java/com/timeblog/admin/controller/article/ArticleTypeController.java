package com.timeblog.admin.controller.article;

import com.google.common.collect.Lists;
import com.timeblog.business.base.BaseController;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.PageDomain;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private RedisUtils redisUtils;


    @RequestMapping("/toArticleType")
    public ModelAndView toArticleType(){
        List<ArticleType> articleTypes = articleTypeMapper.queryAll(new PageDomain());
        articleTypes = articleTypes.stream().sorted(Comparator.comparing(ArticleType::getTypeName)).collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("article/articleType");
        modelAndView.addObject("articleTypes",articleTypes);
        return modelAndView;
    }


    @RequestMapping("/showArticleTypeByParentId")
    @ResponseBody
    public Result showArticleTypeByParentId(@RequestBody ArticleType articleType){
        List<ArticleType> articleTypeList =  articleTypeMapper.queryByParentId(Integer.parseInt(articleType.getParentId()));
        return Result.success(articleTypeList);
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
        Optional.ofNullable(articleType).ifPresent(at ->{
            if (at.getIsAvailable() == null) {
                at.setIsAvailable(0);
            }
            if(StringUtils.isEmpty(at.getParentId())){
                at.setParentId("-1");
            }
            //序列先不做
            at.setSort(0);
        });
        List<ArticleType> articleTypes = (List<ArticleType>) redisUtils.get(SystemConstant.TEMP_ARTICLE_TYPES);
        if (articleTypes == null){
            articleTypes = Lists.newArrayList(articleType);
        }else{
            articleTypes.add(articleType);
        }
        redisUtils.set(SystemConstant.TEMP_ARTICLE_TYPES,articleTypes);
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
