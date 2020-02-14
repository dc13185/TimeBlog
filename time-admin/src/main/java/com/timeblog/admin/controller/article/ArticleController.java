package com.timeblog.admin.controller.article;

import com.timeblog.business.base.Result;
import com.timeblog.system.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author DongChao
 * @Classname ArticleController
 * @Description 文章业务
 * @Date 2020/2/9 10:27
 * @Version V1.0
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final static Logger logger =LoggerFactory.getLogger(ArticleController.class);

    @Value("${imageFile.path}")
    private String imageFilePath;

    @Value("${imageFile.url}")
    private String imageUrl;

    @RequestMapping("/toEditArticle")
    public String toArticleType(){
        return "article/editArticle";
    }


    /**
     * @author: dongchao
     * @create: 2020/2/11
     * @description: editor.md 上传图片
     * @return: 
     * @param: file
     */
    @RequestMapping("uploadImage")
    @ResponseBody
    public Result uploadImage(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request){
        try {
            String urlPath = LocalDate.now().toString()+"/"+FileUtils.getRandomBySrcFileName(file.getOriginalFilename());
            String imgFilePath = imageFilePath+urlPath;
            Path targetPath = Paths.get(imgFilePath);
            //父文件夹不存在就创建
            if (!Files.exists(targetPath.getParent())){
                Files.createDirectories(targetPath.getParent());
            }
            //复制文件
            Files.copy(file.getInputStream(),targetPath);
            //拼接图片路径
            String ip = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath();
            String url = ip + imageUrl.replace("**","")+urlPath;
            return   Result.success().add("success",1).add("message","上传成功").add("url",url).add("title",file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")));
        String imageUrl = "/images/**";
        String url = imageUrl.replace("**","").substring(1);
        System.out.println(url);
    }


}
