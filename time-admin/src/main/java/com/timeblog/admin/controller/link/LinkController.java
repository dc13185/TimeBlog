package com.timeblog.admin.controller.link;

import com.timeblog.business.base.Result;


import com.timeblog.business.domain.Article;
import com.timeblog.business.domain.Link;
import com.timeblog.business.domain.PageDomain;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Dong.Chao
 * @Classname LinkController
 * @Description 友链业务层
 * @Date 2020/3/12 14:25
 * @Version V1.0
 */
@Controller
@RequestMapping("/link")
public class LinkController {

    @Value("${imageFile.path}")
    private String imageFilePath;

    @Value("${imageFile.url}")
    private String imageUrl;

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private LinkMapper linkMapper;

    @RequestMapping("/toLinkList")
    public String toCommentList(){
        return "link/linkList";
    }


    @RequestMapping("/showLinkList")
    @ResponseBody
    public Result showLinkListWithPage(@RequestBody PageDomain pageDomain){
        List<Link> linkList = linkMapper.queryAlls(pageDomain);
        return Result.success(linkList);
    }

    @RequestMapping("/toInsertLink")
    public ModelAndView toInsertLink(){
        ModelAndView modelAndView = new ModelAndView("link/insertLink");

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
        if (!StringUtils.contains(hashMap.get("linkImage"),SystemConstant.SOURCE_IMG)){
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

    @RequestMapping("/editLinkStatus")
    @ResponseBody
    public Result editLinkStatus(@RequestBody HashMap<String,String> hashMap) {
        String linkStatus=hashMap.get("linkStatus");
            System.out.println(linkStatus);
        String linkId=hashMap.get("linkId");
        Link link=new Link();
        link.setLinkStatus(Integer.parseInt(linkStatus));
        link.setLinkId(Integer.parseInt(linkId));
        linkMapper.updateStatus(link);
        return Result.success();
    }

    @RequestMapping("/deleteLink")
    @ResponseBody
    public Result deleteLink(@RequestBody HashMap<String,String> hashMap){
        String linkIds=hashMap.get("linkIds");
        List<String> linkIdList= Arrays.asList(linkIds.split(","));
        for (int i=0;i<linkIdList.size();i++) {
            Link link=linkMapper.queryById(Integer.parseInt(linkIdList.get(i)));
            if (link.getLinkImage() != null && !link.getLinkImage().contains(SystemConstant.BLANK_IMG)){
                Matcher m = SystemConstant.IMAGE_PATTERN.matcher(link.getLinkImage());
                if(m.find()){
                    String imagePath = m.group(0);
                    Path path = Paths.get(imageFilePath + imagePath);
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        linkMapper.deleteLinkByIds(linkIdList);
        return Result.success();
    }


}