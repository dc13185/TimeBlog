package com.timeblog.admin.controller.article;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.timeblog.admin.config.constant.SystemConstant;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.Article;
import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.PageDomain;
import com.timeblog.framework.mapper.ArticleMapper;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import com.timeblog.framework.system.utils.BeanUtils;
import com.timeblog.framework.system.utils.FileUtils;
import com.timeblog.framework.system.utils.IpUtils;
import com.timeblog.framework.system.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import us.codecraft.webmagic.utils.IPUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

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

    @Autowired
    private  RedisUtils redisUtils;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleTypeMapper articleTypeMapper;

    /**
     * @author: dongchao
     * @create: 2020/2/18-18:00
     * @description: 跳转至修改文章页面
     * @param:
     * @return:
     */
    @RequestMapping("/toEditArticle")
    public ModelAndView toArticleType(){
        ModelAndView modelAndView = new ModelAndView("article/editArticle");
        String context = (String)redisUtils.get(SystemConstant.TEMP_ARTICLE_FLAG);
        if (null != context){
            modelAndView.addObject("tempArticle",context);
        }
        return modelAndView;
    }

    /**
     * @author: dongchao
     * @create: 2020/2/18-18:00
     * @description: 完成修改文章页面
     * @param:
     * @return:
     */
    @RequestMapping("/toCompleteArticle")
    public ModelAndView toCompleteArticle(String articleId) throws Exception{
        //查出所有的分类
        List<ArticleType> articleTypes = articleTypeMapper.queryAll(new PageDomain());
        articleTypes = articleTypes.stream().sorted(Comparator.comparing(ArticleType::getTypeName)).collect(Collectors.toList());
        Article article = (Article) redisUtils.get(SystemConstant.TEMP_ARTICLE_FLAG+articleId);
        if (article == null){
            article = articleMapper.queryById(Integer.parseInt(articleId));
            redisUtils.set(SystemConstant.TEMP_ARTICLE_FLAG+articleId,article);
        }
        //返回modelAndView
        ModelAndView modelAndView = new ModelAndView("article/otherArticle");
        modelAndView.addObject("article",article);
        modelAndView.addObject("articleTypes",articleTypes);
        return modelAndView;
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
    public Result uploadImage(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request) throws Exception{
        String urlPath = LocalDate.now().toString()+"/"+ FileUtils.getRandomBySrcFileName(file.getOriginalFilename());
        String imgFilePath = imageFilePath+urlPath;
        Path targetPath = Paths.get(imgFilePath);
        //父文件夹不存在就创建
        if (!Files.exists(targetPath.getParent())){
            Files.createDirectories(targetPath.getParent());
        }
        //复制文件
        Files.copy(file.getInputStream(),targetPath);
        //拼接图片路径
        String ip = IpUtils.getProjectPath(request);
        String url = ip + imageUrl.replace("**","")+urlPath;
        //加添图片路径到redis
        List<String> tempList = (List<String>) redisUtils.get(SystemConstant.TEMP_ARTICLE_IMAGES_FLAG);
        if (null == tempList){
            tempList = Lists.newArrayList(url);
        }else {
            tempList.add(url);
        }
        redisUtils.set(SystemConstant.TEMP_ARTICLE_IMAGES_FLAG, tempList);
        return   Result.success().add("success",1).add("message","上传成功").add("url",url).add("title",file.getOriginalFilename());
    }


    /**
     * @author: dongchao
     * @create: 2020/2/17
     * @description:存临时文章草稿。系统中只会有一份临时草稿
     * @param:
     * @return:
     */
    @RequestMapping("saveTempArticle")
    @ResponseBody
    public Result saveTempArticle(@RequestBody Article article) throws Exception{
        if (-1 != article.getArticleId()){
            article = article.toBuilder().status(0).build();
            //将草稿存入redis
            redisUtils.set(SystemConstant.TEMP_ARTICLE_FLAG+article.getArticleId(),article);
            articleMapper.update(article);
        }else {
            if (StringUtils.isEmpty(article.getArticleTitle())){
                //如果没有设置标题，可认为存临时草稿
                String mdContext = article.getArticleContextMd();
                redisUtils.set(SystemConstant.TEMP_ARTICLE_FLAG,mdContext);
            }else{
                //如果有标题，则可设置为草稿 ,设置默认值
                article = article.toBuilder().status(0).isComment(1).isOriginal(1).isTop(0).build();
                articleMapper.insert(article);
                //草稿存入redis
                String articleContext = article.getArticleContextMd();
                redisUtils.set(SystemConstant.TEMP_ARTICLE_FLAG+article.getArticleId(),article);
                List<String> tempList = (List<String>) redisUtils.get(SystemConstant.TEMP_ARTICLE_IMAGES_FLAG);
                if (null != tempList){
                    //如果临时图片内容不为空
                    tempList.forEach(image ->{
                        if (!articleContext.contains(image)){
                            //如果不含包的话，获取文件名，将此文件删除
                            Matcher m = SystemConstant.IMAGE_PATTERN.matcher(image);
                            if(m.find()){
                                String imagePath = m.group(0);
                                Path path = Paths.get(imageFilePath + imagePath);
                                try {
                                    Files.delete(path);
                                } catch (IOException e) {
                                    //lambda无法抛出受查异常，只能抛出运行异常
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                    redisUtils.remove(SystemConstant.TEMP_ARTICLE_IMAGES_FLAG);
                }
                redisUtils.remove(SystemConstant.TEMP_ARTICLE_FLAG);
            }
        }

        return  Result.success().add("articleId",article.getArticleId());
    }


    /**
     * @author: dongchao
     * @create: 2020/2/20-16:25
     * @description:完善所有文章配置
     * @param:
     * @return:
     */
    @RequestMapping("completeArticle")
    @ResponseBody
    public Result completeArticle(@RequestBody Article article,HttpServletRequest request) throws Exception{
        //上传封面
        if (!StringUtils.contains(article.getCoverImage(),SystemConstant.SOURCE_IMG)){
            String nowDate = LocalDate.now().toString();
            String filePath =  imageFilePath + nowDate+"/";
            String imageFileName = FileUtils.GenerateImage(article.getCoverImage(),filePath);
            String ip = IpUtils.getProjectPath(request);
            String url = ip + imageUrl.replace("**","")+nowDate+"/"+imageFileName;
            //修改URL 路径
            article = article.toBuilder().coverImage(url).build();
            //判断其原来的值是否
            Article srcArticle =  (Article)redisUtils.get(SystemConstant.TEMP_ARTICLE_FLAG+article.getArticleId());
            if (srcArticle == null){
                srcArticle = articleMapper.queryById(article.getArticleId());
            }
            //如果原来的封面是上传的，就删除
            if (srcArticle.getCoverImage() != null && !srcArticle.getCoverImage().contains(SystemConstant.SOURCE_IMG)){
                Matcher m = SystemConstant.IMAGE_PATTERN.matcher(srcArticle.getCoverImage());
                if(m.find()){
                    String imagePath = m.group(0);
                    Path path = Paths.get(imageFilePath + imagePath);
                    Files.delete(path);
                }
            }
        }


        redisUtils.set(SystemConstant.TEMP_ARTICLE_FLAG+article.getArticleId(),article);
        articleMapper.update(article);

        return  Result.success();
    }



    public static void main(String[] args) {
        String a = "www.asdasdh.hahah/qiumingshan/2019-01-12/hahahha.png";
        Matcher m = SystemConstant.IMAGE_PATTERN.matcher(a);
        if(m.find()){
            System.out.println(m.group(0));
        }
    }


}
