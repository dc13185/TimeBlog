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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
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

    @Resource
    private CommentDao commentDao;

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
    public Result commentRepaly(@RequestBody Comment reqComment) throws UnknownHostException {
        String picture = null;
        String nickName = null;
        String commentQQ=SystemConstant.BLOGWEBCONFIG.getBlogAuthorQq();
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
        ip= InetAddress.getLocalHost().getHostAddress();
        if ((adress=="") || (adress==null)){
            adress = "火星";
        }
        if (StringUtils.isBlank(nickName)){
            nickName = "匿名";
        }
        //comment入库
        Comment comment = reqComment.toBuilder().commentAddress(adress)
                .commentIp(ip)
                .commentQQ(commentQQ)
                .createTime(new Date())
                .commentNickname(nickName)
                .commentPicture(picture)
                .commentStatus(0).build();
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
