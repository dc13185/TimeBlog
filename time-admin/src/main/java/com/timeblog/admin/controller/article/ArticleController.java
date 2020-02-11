package com.timeblog.admin.controller.article;

import com.timeblog.business.base.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.logging.LogManager;

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
    public Result uploadImage(@RequestParam("editormd-image-file") MultipartFile file){

        System.out.println(111);
        return null;
    }


}
