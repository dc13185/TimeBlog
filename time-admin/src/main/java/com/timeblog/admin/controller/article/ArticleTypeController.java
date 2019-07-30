package com.timeblog.admin.controller.article;

import com.timeblog.business.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: dong.chao
 * @create: 2019-07-29 20:20
 * @description: 文章类型
 **/
@Controller
@RequestMapping("/articleType")
public class ArticleTypeController extends BaseController {

    @RequestMapping("/toArticleType")
    public String toArticleType(){
        return "article/articleType";
    }
}
