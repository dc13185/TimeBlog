package com.timeblog.admin.controller.article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lmm
 * @Classname ArticleController
 * @Description 文章业务
 * @Date 2020/2/9 10:27
 * @Version V1.0
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @RequestMapping("/toEditArticle")
    public String toArticleType(){
        return "article/editArticle";
    }


}
