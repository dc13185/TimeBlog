package com.timeblog.web.controller;


import com.timeblog.business.base.Result;
import com.timeblog.business.domain.Link;

import com.timeblog.framework.mapper.LinkMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * @author clw
 * @Classname LinksController
 * @Description webLink业务
 * @Date 2020/3/12 14:13
 * @Version V1.0
 */
@Controller
@RequestMapping("/web/link")
public class WebLinkController {


    @Value("${imageFile.path}")
    private String imageFilePath;

    @Value("${imageFile.url}")
    private String imageUrl;

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private LinkMapper linkMapper;

    @RequestMapping("/toLink")
    public ModelAndView toWebLink() {
        List<Link> linkList=linkMapper.queryAll();
        ModelAndView modelAndView = new ModelAndView("web/link");
        modelAndView.addObject("linkList",linkList);
        return modelAndView;
    }

    @RequestMapping("/toInsertLink")
    public ModelAndView toInsertLink(){
        ModelAndView modelAndView = new ModelAndView("web/toInsertLink");

        return modelAndView;
    }
    @RequestMapping("/insertLink")
    @ResponseBody
    public Result insertLink(@RequestBody HashMap<String,String> hashMap, HttpServletRequest request) throws Exception {
        Link link=new Link();
        String linkImage=hashMap.get("linkImage");
        link.setLinkImage(linkImage);
        link.setLinkStatus(Integer.parseInt(hashMap.get("linkStatus")));
        link.setLinkDescription(hashMap.get("linkDescription"));
        link.setLinkHerf(hashMap.get("linkHerf"));
        link.setLinkName(hashMap.get("linkName"));

        //上传头像
        if (!StringUtils.contains(hashMap.get("linkImage"), SystemConstant.SOURCE_IMG)){
            String nowDate = LocalDate.now().toString();
            String filePath =  imageFilePath + nowDate+"/";
            String imageFileName = FileUtils.GenerateImage(linkImage,filePath);
            String ip = IpUtils.getProjectPath(request);
            String url = ip + imageUrl.replace("**","")+nowDate+"/"+imageFileName;
            //修改URL 路径
            link = link.toBuilder().linkImage(url).build();

        }
        linkMapper.insert(link);
        return Result.success();
    }
}