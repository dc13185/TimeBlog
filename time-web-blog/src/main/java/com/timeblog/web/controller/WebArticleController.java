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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
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
    private RedisUtils redisUtils;


    @Autowired
    private ArticleMapper articleMapper;



    @RequestMapping("/toArticles")
    public ModelAndView toArticles(){
        ModelAndView modelAndView = new ModelAndView("web/article");
        //查找首级分类
        List<ArticleType> articleTypeList =  (List<ArticleType>) redisUtils.get(SystemConstant.TEMP_ARTICLE_TYPES);
        articleTypeList =  articleTypeList.stream().filter(l -> "-1".equals(l.getParentId())).collect(Collectors.toList());
        Integer articleCount = articleMapper.queryCount();
        BlogWebConfig blogWebConfig = (BlogWebConfig)redisUtils.get(SystemConstant.WEB_BLOG_CONFIG);
        modelAndView.addObject("articleTypeList",articleTypeList)
                    .addObject("blogWebConfig",blogWebConfig)
                    .addObject("articleCount",articleCount);
        return modelAndView;
    }


    @RequestMapping("/toArticleDetails")
    public ModelAndView toArticleDetails(String articleId,HttpServletRequest request){
        if (StringUtils.isEmpty(articleId)){
           new Exception("articleId is empty");
        }
        Article article = articleMapper.queryById(Integer.parseInt(articleId));
        if (article == null){
            new Exception("Page is not  find");
        }
        //浏览数
        Long accessCount = redisUtils.hmIncrement(SystemConstant.WEB_BLOG_ARTICLE_ACCESS_COUNT,articleId,1L);
        //点赞数
        Integer likeCount  = (Integer) redisUtils.hmGet(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT,articleId);
        if (likeCount == null){
            likeCount = 0;
        }
        //是否点赞
        int likeStatus = 0;
        Set<Object> likeUsers =  redisUtils.setMembers(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT+articleId);
        String userIpAddr = request.getRemoteAddr();
        if (likeUsers.contains(userIpAddr)){
            likeStatus = 1;
        }
        BlogWebConfig blogWebConfig = (BlogWebConfig)redisUtils.get(SystemConstant.WEB_BLOG_CONFIG);
        ModelAndView modelAndView = new ModelAndView("web/read");
        modelAndView.addObject("article",article)
                    .addObject("blogWebConfig",blogWebConfig)
                    .addObject("accessCount",accessCount)
                    .addObject("likeCount",likeCount)
                    .addObject("likeStatus",likeStatus);
        return modelAndView;
    }


    @RequestMapping("/showArticleList")
    @ResponseBody
    public Result showArticleListWithPage(@RequestBody PageDomain pageDomain){
        //查询所有已经发布了的
        List<Article> articleTypeList =  articleMapper.queryAllFormal(pageDomain);
        //点赞浏览的
        List<String> articleIds = articleTypeList.stream().map(article -> article.getArticleId().toString()).collect(Collectors.toList());
        //批量顺序不会乱
        List<Integer> accessCount = redisUtils.batchHmGet(SystemConstant.WEB_BLOG_ARTICLE_ACCESS_COUNT,articleIds);
        List<Integer>  likeCount = redisUtils.batchHmGet(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT,articleIds);
        for (int i = 0; i < articleTypeList.size(); i++) {
            articleTypeList.get(i).setLikeCount(likeCount.get(i));
            articleTypeList.get(i).setAccessCount(accessCount.get(i));
        }
        return Result.success(articleTypeList);
    }


    @RequestMapping("/articleLike")
    @ResponseBody
    public Result articleLike(@RequestBody Article article, HttpServletRequest request){
        String articleId = article.getArticleId().toString();
        Long likeCount = 1L;
        //如果没有登录获取游客Ip
        String userIpAddr = request.getRemoteAddr();
        Set<Object> likeUsers =  redisUtils.setMembers(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT+articleId);
        if (likeUsers != null){
            //如果包含则为取消点赞
            if(likeUsers.contains(userIpAddr)){
                redisUtils.remove(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT+article.getArticleId().toString(),userIpAddr);
                likeCount = -1L;
            }else {
                redisUtils.add(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT+articleId,userIpAddr);
            }
        }else {
            redisUtils.add(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT+articleId,userIpAddr);
        }

        redisUtils.hmIncrement(SystemConstant.WEB_BLOG_ARTICLE_LIKE_COUNT,articleId,likeCount);
        return Result.success();
    }





}
