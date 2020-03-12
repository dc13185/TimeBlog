package com.timeblog.admin.controller.comment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.*;
import com.timeblog.framework.mapper.*;
import com.timeblog.framework.system.constant.InterfaceConstant;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.HttpClientUtils;
import com.timeblog.framework.system.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author clw
 * @Classname CommentsController
 * @Description 文章业务
 * @Date 2020/3/9 10:27
 * @Version V1.0
 */
@Controller
@RequestMapping("/comment")
public class CommentsController {



    @Autowired
    private  RedisUtils redisUtils;

    @Resource
    private CommentDao commentDao;

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
    public Result showCommentListWithPage(@RequestBody PageDomain pageDomain) throws ParseException {
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
    @RequestMapping("/commentReplay")
    @ResponseBody
    public Result commentRepaly(@RequestBody HashMap<String,String> map){
        String replayId = map.get("replayId");
        Integer articleId=commentDao.queryById(Integer.parseInt(replayId)).getCommentArticleId();
        String picture=null;
        Article article=articleMapper.queryById(articleId);
        String nickName=null;
        String commentContent=map.get("commentContent");
        String commentQQ=SystemConstant.BLOGWEBCONFIG.getBlogAuthorQq();
        Comment comment=new Comment();
        if (StringUtils.isNotEmpty(commentQQ)){
            //QQ不为空获取其头像，昵称
            Map<String,String> infoMap = null;
            try {
                infoMap = getPictureAndNickNameByQq(commentQQ);
            } catch (IOException e) {
                e.printStackTrace();
            }
            picture = infoMap.get("picture");
            nickName = infoMap.get("nickName");
        }
        String adress=null;
        String ip=null;
        try {
             ip= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if ((adress=="") || (adress==null)){
            adress = "火星";
        }
        if (StringUtils.isBlank(nickName)){
            nickName = "匿名";
        }
        comment.setCommentAddress(adress);
//        comment.setCommentArticleId(articleId);
        comment.setCommentIp(ip);
        comment.setCommentQQ(commentQQ);
        comment.setParentCommentId(Integer.parseInt(replayId));
//        comment.setCommentArticleName(article.getArticleTitle());
        comment.setCommentContent(commentContent);
        comment.setCreateTime(new Date());
        comment.setCommentNickname(nickName);
        comment.setCommentPicture(picture);
        comment.setReplyId(Integer.parseInt(replayId));
        comment.setCommentStatus(0);
        commentDao.insert(comment);
        return Result.success();
    }


    private Map<String,String> getPictureAndNickNameByQq(String Qq) throws IOException {
        String nickName = null;
        String picture = String.format(InterfaceConstant.GET_QQ_PICTURE_INTERFACE,Qq);
        String getNickNameUrl = String.format(InterfaceConstant.GET_QQ_NICKNAME_INTERFACE,Qq);
        String  resultStr =  HttpClientUtils.getRequest(getNickNameUrl);
        if (StringUtils.isNotEmpty(resultStr)){
            String jsonContent = StringUtils.substringBetween(resultStr,"portraitCallBack(",")");
            JSONObject jsonObject = JSONObject.parseObject(jsonContent);
            JSONArray jsonArray =  jsonObject.getJSONArray(Qq);
            String nikNameStr =  jsonArray.getString(6);
            if (StringUtils.isNotBlank(nikNameStr)){
                //获取昵称
                nickName = nikNameStr;
            }
        }

        Map<String,String> hashMap = Maps.newHashMap();
        hashMap.put("nickName",nickName);
        hashMap.put("picture",picture);

        return hashMap;
    }

}
