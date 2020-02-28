package com.timeblog.admin.controller.config;

import com.timeblog.business.base.Result;
import com.timeblog.business.domain.BlogWebConfig;
import com.timeblog.business.domain.WebBackground;
import com.timeblog.framework.mapper.BlogWebConfigDao;
import com.timeblog.framework.mapper.WebBackgroundDao;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.FileUtils;
import com.timeblog.framework.system.utils.IpUtils;
import com.timeblog.framework.system.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

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

    @Resource
    private BlogWebConfigDao blogWebConfigDao;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/toConfig")
    public ModelAndView toConfig(){
        BlogWebConfig blogWebConfig = blogWebConfigDao.queryAll();
        if (blogWebConfig == null){
            blogWebConfig = new BlogWebConfig();
        }
        List<WebBackground> webBackgroundList = webBackgroundDao.queryAll();
        ModelAndView modelAndView = new ModelAndView("config/webConfig");
        modelAndView.addObject("webBackgroundList",webBackgroundList)
                    .addObject("blogWebConfig",blogWebConfig);
        return modelAndView;
    }


    @RequestMapping("/toBackGroundConfig")
    public ModelAndView toBackGroundConfig(){
        List<WebBackground> webBackgroundList = webBackgroundDao.queryAll();
        ModelAndView modelAndView = new ModelAndView("config/BackGroundConfig");
        modelAndView.addObject("webBackgroundList",webBackgroundList);
        return modelAndView;
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


    /**
     * @author: dongchao
     * @create: 2020/2/27-22:06
     * @description: 设置系统配置
     * @param:
     * @return:
     */
    @RequestMapping("/settingBlogConfig")
    @ResponseBody
    public Result settingBlogConfig(@RequestBody BlogWebConfig blogWebConfig){
        BlogWebConfig flag = blogWebConfigDao.queryAll();
        if(flag == null){
            blogWebConfigDao.insert(blogWebConfig);
        }else{
            blogWebConfigDao.update(blogWebConfig);
        }
        redisUtils.set(SystemConstant.WEB_BLOG_CONFIG,blogWebConfig);
        return Result.success();
    }





}
