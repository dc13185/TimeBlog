package com.timeblog.admin.controller.comment;

import com.timeblog.business.base.Result;
import com.timeblog.business.domain.*;
import com.timeblog.framework.mapper.*;
import com.timeblog.framework.system.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author DongChao
 * @Classname ArticleController
 * @Description 文章业务
 * @Date 2020/2/9 10:27
 * @Version V1.0
 */
@Controller
@RequestMapping("/comment")
public class CommentsController {

    private final static Logger logger =LoggerFactory.getLogger(CommentsController.class);

    @Value("${imageFile.path}")
    private String imageFilePath;

    @Value("${imageFile.url}")
    private String imageUrl;

    @Autowired
    private  RedisUtils redisUtils;

    @Resource
    private CommentDao commentDao;

    @Resource
    private ArticleTypeMapper articleTypeMapper;

    @Resource
    private LabelMapper labelMapper;

    @Resource
    private ArticleLabelMapper articleLabelMapper;

    @Resource
    private ArticleMapper articleMapper;

    @RequestMapping("/toCommentList")
    public String toCommentList(){
        return "comment/commentList";
    }

    @RequestMapping("/showCommentList")
    @ResponseBody
    public Result showCommentListWithPage(@RequestBody PageDomain pageDomain){
        List<Comment> commentList =  commentDao.queryAll(pageDomain);
        return Result.success(commentList);
    }


    @RequestMapping("/deleteComment")
    @ResponseBody
    public Result delete(@RequestBody HashMap<String,String> map){
        String commentIds = map.get("commentIds");
        List<String> commentIdsList = Arrays.asList(commentIds.split(","));
        commentDao.deleteByIds(commentIdsList);
        return Result.success();
    }

}
