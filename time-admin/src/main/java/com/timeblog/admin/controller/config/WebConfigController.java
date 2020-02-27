package com.timeblog.admin.controller.config;

import com.timeblog.business.base.Result;
import com.timeblog.business.domain.WebBackground;
import com.timeblog.framework.mapper.WebBackgroundDao;
import com.timeblog.framework.system.utils.FileUtils;
import com.timeblog.framework.system.utils.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * @author Dong.Chao
 * @Classname WebConigController
 * @Description blog系统设置
 * @Date 2020/2/27 11:17
 * @Version V1.0
 */

@Controller
@RequestMapping("/config")
public class WebConfigController {

    @Value("${imageFile.path}")
    private String imageFilePath;

    @Value("${imageFile.url}")
    private String imageUrl;

    @Resource
    private WebBackgroundDao webBackgroundDao;

    @RequestMapping("/toConfig")
    public String toConfig(){
        return "config/webConfig";
    }


    @RequestMapping("/toBackGroundConfig")
    public String toBackGroundConfig(){
        return "config/BackGroundConfig";
    }


    @RequestMapping("/uploadBackGroundImage")
    @ResponseBody
    public Result uploadBackGroundImage(@RequestBody HashMap hashMap, HttpServletRequest request) throws Exception {
        String fileData = (String)hashMap.get("fileData");
        String backGroundName = (String)hashMap.get("backGroundName");
        String url = FileUtils.uploadBase64Image(fileData,imageFilePath,imageUrl,request);
        WebBackground webBackground = WebBackground.builder().backgroundUrl(url).backgroundName(backGroundName).build();
        webBackgroundDao.insert(webBackground);
        //修改URL 路径
        return Result.success().add("url",url);
    }





}
