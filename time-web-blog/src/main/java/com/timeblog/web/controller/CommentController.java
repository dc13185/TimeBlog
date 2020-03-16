package com.timeblog.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.Comment;
import com.timeblog.framework.mapper.CommentDao;
import com.timeblog.framework.system.constant.InterfaceConstant;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.HttpClientUtils;
import com.timeblog.framework.system.utils.Md5Utils;
import com.timeblog.framework.system.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dong.Chao
 * @Classname CommentController
 * @Description 评论业务
 * @Date 2020/3/5 21:45
 * @Version V1.0
 */
@Controller
@RequestMapping("/web/comment")
public class CommentController {

    @Resource
    private CommentDao commentDao;


    /**
     * @author: dongchao
     * @create: 2020/3/5-21:46
     * @description:评论接口
     * @param:
     * @return:
     */
    @RequestMapping("submitComment")
    @ResponseBody
    public Result submitComment(@RequestBody Comment reqComment, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        //获取IP
        String userIpAddr = request.getRemoteAddr();
        String picture = null , nickName = null , address="";
        //获取QQ头像、昵称
        if (StringUtils.isNotEmpty(reqComment.getCommentQQ())){
            //QQ不为空获取其头像，昵称
            Map<String,String> infoMap = getPictureAndNickNameByQq(reqComment.getCommentQQ());
            picture = infoMap.get("picture");
            nickName = infoMap.get("nickName");
        }else{
            if(StringUtils.isNotBlank(reqComment.getCommentMail()) && reqComment.getCommentMail().contains("@qq")){
                String qq = StringUtils.substringBefore(reqComment.getCommentMail(),"@");
                Map<String,String> infoMap = getPictureAndNickNameByQq(qq);
                picture = infoMap.get("picture");
                nickName = infoMap.get("nickName");
            }
        }

        //获取ip定位
        String ipAddressSing =    "/ws/location/v1/ip?ip="+userIpAddr+"&key="+ InterfaceConstant.APP_KEY +InterfaceConstant.SECRET_KEY;
        String sing = Md5Utils.encode(ipAddressSing);
        String getAddressUrl = InterfaceConstant.GET_IP_ADDRESS_INTERFACE+userIpAddr+"&key="+ InterfaceConstant.APP_KEY+"&sig="+sing;
        String result = HttpClientUtils.getRequest(getAddressUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject resultObj =  jsonObject.getJSONObject("result");
        Integer status = jsonObject.getInteger("status");
        if (status == 0){
            JSONObject addressInfo = resultObj.getJSONObject("ad_info");
            String nation = addressInfo.getString("nation");
            String province = addressInfo.getString("province");
            String city = addressInfo.getString("city");
            address = nation + province + city;
        }

        if (StringUtils.isBlank(address)){
            address = "火星";
        }
        if (StringUtils.isBlank(nickName)){
            nickName = "匿名";
        }

        //评论内容入库
        Comment comment = reqComment.toBuilder().createTime(new Date())
                            .commentIp(userIpAddr)
                            .commentNickname(nickName)
                            .commentPicture(picture)
                            .commentAddress(address)
                            .commentStatus(0)
                            .build();
        commentDao.insert(comment);
        return Result.success().add("comment",comment);
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


    @RequestMapping("toMessage")
    public ModelAndView toMessage(){
        //评论
        List<Comment> srcComments = commentDao.queryCommentByMessage();
        List<Comment> comments = srcComments.stream().filter(comment -> comment.getParentCommentId() == null).collect(Collectors.toList());
        comments.forEach(comment -> {
            List sonComment = srcComments.stream().filter(c -> c.getParentCommentId() != null && (c.getParentCommentId().equals(comment.getCommentId())))
                    .sorted(Comparator.comparing(Comment::getCreateTime)).collect(Collectors.toList());
            comment.setSonComments(sonComment);
        });

        ModelAndView modelAndView = new ModelAndView("web/message");
        return modelAndView.addObject("blogWebConfig",SystemConstant.BLOGWEBCONFIG)
                           .addObject("comments",comments);
    }

    public static void demo2(String str){
        str = str + "abc";
    }

    public static void main(String[] args) {
        String s1 = "abcd";
        System.out.println(s1);
    }


}
