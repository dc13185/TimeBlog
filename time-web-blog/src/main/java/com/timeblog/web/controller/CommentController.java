package com.timeblog.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.Comment;
import com.timeblog.framework.mapper.CommentDao;
import com.timeblog.framework.system.constant.InterfaceConstant;
import com.timeblog.framework.system.utils.HttpClientUtils;
import com.timeblog.framework.system.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

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


        //留言
        Comment comment = reqComment.toBuilder().createTime(new Date())
                            .commentIp(userIpAddr)
                            .commentNickname(nickName)
                            .commentPicture(picture)
                            .commentAddress(address)
                            .commentStatus(0)
                            .build();

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


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String qq = StringUtils.substringBefore("1318533144@qq.com","@");
        //测试提交代码 测试修改
        System.out.println(qq);
    }
}
