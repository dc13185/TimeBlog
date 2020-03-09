package com.timeblog.admin.controller.article;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.*;
import com.timeblog.framework.mapper.ArticleLabelMapper;
import com.timeblog.framework.mapper.ArticleMapper;
import com.timeblog.framework.mapper.ArticleTypeMapper;
import com.timeblog.framework.mapper.LabelMapper;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.FileUtils;
import com.timeblog.framework.system.utils.IpUtils;
import com.timeblog.framework.system.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
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

    @Resource
    private LabelMapper labelMapper;

    @Resource
    private ArticleLabelMapper articleLabelMapper;




    @RequestMapping("/toArticleList")
    public String toArticleList(){
        return "article/articleList";
    }

    @RequestMapping("/showArticleList")
    @ResponseBody
    public Result showArticleListWithPage(@RequestBody PageDomain pageDomain){
        List<Article> articleTypeList =  articleMapper.queryAll(pageDomain);
        return Result.success(articleTypeList);
    }


    /**
     * @author: dongchao
     * @create: 2020/2/18-18:00
     * @description: 跳转至修改文章页面
     * @param:
     * @return:
     */
    @RequestMapping("/toEditArticle")
    public ModelAndView toEditArticle(String articleId){
        ModelAndView modelAndView = new ModelAndView("article/editArticle");
        String context = (String)redisUtils.get(SystemConstant.TEMP_ARTICLE_FLAG);
        if (StringUtils.isNotEmpty(articleId)){
            Article article = articleMapper.queryById(Integer.parseInt(articleId));
            context = article.getArticleContextMd();
            modelAndView.addObject("articleId",article.getArticleId());
            modelAndView.addObject("articleTitle",article.getArticleTitle());
        }
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
        ModelAndView modelAndView = new ModelAndView("article/otherArticle");
        //查出所有的分类
        List<ArticleType> articleTypes = articleTypeMapper.queryNotParentNode();
        articleTypes = articleTypes.stream().sorted(Comparator.comparing(ArticleType::getTypeName)).collect(Collectors.toList());
        Article article = articleMapper.queryById(Integer.parseInt(articleId));
        //查出文章对应标签
        List<Label> articleToLabels;
        HashMap<String,List<Label>> articleToLabelMap = (HashMap<String,List<Label>>) redisUtils.get(SystemConstant.ARTICLE_TO_LABEL_FLAG);
        if (articleToLabelMap == null){
            articleToLabelMap = Maps.newHashMap();
            articleToLabels =  labelMapper.queryLabelsByArticleId(Integer.parseInt(articleId));
            articleToLabelMap.put(articleId,articleToLabels);
            redisUtils.set(SystemConstant.ARTICLE_TO_LABEL_FLAG,articleToLabelMap);
        }else{
            articleToLabels = articleToLabelMap.get(articleId);
        }

        if (articleToLabels != null){
            List<Integer> articleToLabelIds = articleToLabels.stream().map(Label::getLabelId).collect(Collectors.toList());
            modelAndView.addObject("articleToLabels",articleToLabelIds);
        }
        //查出所有标签
        List<Label> allArticleLabel = (List<Label>) redisUtils.get(SystemConstant.ARTICLE_LABEL_FLAG);
        if (allArticleLabel == null){
            allArticleLabel = labelMapper.queryAll(null);
            redisUtils.set(SystemConstant.ARTICLE_LABEL_FLAG,allArticleLabel);
        }

        //返回modelAndView
        modelAndView.addObject("allArticleLabel",allArticleLabel);
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
        //先设默认值
        article.setUpdateTime(new Date());
        if (null != article.getArticleId()){
            //如果是修改，取临时存储的article(原数据),这里只是修改了标题和文本内容
            articleMapper.update(article);
        }else {
            article = article.toBuilder().status(0).isComment(1).isOriginal(1).isTop(0).build();
            article.setCreateTime(new Date());
            if (StringUtils.isEmpty(article.getArticleTitle())){
                //如果没有设置标题，可认为存临时草稿
                String mdContext = article.getArticleContextMd();
                redisUtils.set(SystemConstant.TEMP_ARTICLE_FLAG,mdContext);
            }else{
                //如果有标题，则可设置为草稿 ,设置默认值
                articleMapper.insert(article);
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
        //原文章
        Article srcArticle = articleMapper.queryById(article.getArticleId());
        //上传封面
        if (!StringUtils.contains(article.getCoverImage(),SystemConstant.SOURCE_IMG)){
            String nowDate = LocalDate.now().toString();
            String filePath =  imageFilePath + nowDate+"/";
            String imageFileName = FileUtils.GenerateImage(article.getCoverImage(),filePath);
            String ip = IpUtils.getProjectPath(request);
            String url = ip + imageUrl.replace("**","")+nowDate+"/"+imageFileName;
            //修改URL 路径
            article = article.toBuilder().coverImage(url).build();
            //如果原来的封面是上传的，就删除
            if (srcArticle.getCoverImage() != null && !srcArticle.getCoverImage().contains(SystemConstant.BLANK_IMG)){
                Matcher m = SystemConstant.IMAGE_PATTERN.matcher(srcArticle.getCoverImage());
                if(m.find()){
                    String imagePath = m.group(0);
                    Path path = Paths.get(imageFilePath + imagePath);
                    Files.delete(path);
                }
            }
        }
        //标签上传
        if (StringUtils.isNoneEmpty(article.getLabelIds())){
            String [] labelIds = article.getLabelIds().split(",");
            List<Label> labels =  (List<Label>)redisUtils.get(SystemConstant.ARTICLE_LABEL_FLAG);
            //存入redis 文章的对应标签
            List<Label> articleToLabels = Lists.newArrayList();
            if (labels == null){
                labels = Lists.newArrayList();
            }
            //该文章已存在标签
            List<Label> articleToLabelList = labelMapper.queryLabelsByArticleId(article.getArticleId());
            //需要删除的标签
            List<Integer> presenceLabels = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(articleToLabelList)){
                presenceLabels = articleToLabelList.stream().map(Label::getLabelId).collect(Collectors.toList());
                articleToLabelList = Lists.newArrayList();
            }

            for (String labelFlag : labelIds) {
                Label label = labels.stream().filter(l -> l.getLabelId().toString().equals(labelFlag) || l.getLabelName().equals(labelFlag))
                        .findAny().orElse(null);
                Integer srcArticleId = article.getArticleId();
                Integer srcLabelId;
                //如果找不到，则为新增
                if (label == null) {
                    label = Label.builder().labelName(labelFlag).build();
                    //入库，入redis
                    labelMapper.insert(label);
                    labels.add(label);
                }
                srcLabelId = label.getLabelId();
                articleToLabels.add(label);
                //如果该文章以有的,不包含其中的 需要新增。去除已有的,就是删除的
                if (!presenceLabels.contains(srcLabelId)){
                    //根据文章id找出所有标签
                    ArticleLabel articleLabel = ArticleLabel.builder().labelId(srcLabelId).articleId(srcArticleId).createDate(new Date()).build();
                    articleLabelMapper.insert(articleLabel);
                }else{
                    presenceLabels.remove(srcLabelId);
                }
            }
            //删除其原有的标签
            if (!CollectionUtils.isEmpty(presenceLabels)){
                articleLabelMapper.deleteByArticleIds(presenceLabels);
            }
            //所有标签存入redis
            redisUtils.set(SystemConstant.ARTICLE_LABEL_FLAG, labels);
            //文章对应标签存入redis
            HashMap<String,List<Label>> articleToLabelMap = (HashMap<String, List<Label>>)redisUtils.get(SystemConstant.ARTICLE_TO_LABEL_FLAG);
            if (articleToLabelMap == null){
                articleToLabelMap = Maps.newHashMap();
            }
             articleToLabelMap.put(article.getArticleId()+"",articleToLabels);
            redisUtils.set(SystemConstant.ARTICLE_TO_LABEL_FLAG,articleToLabelMap);
        }
        //数据库中修改
        articleMapper.update(article);
        return  Result.success();
    }


    @RequestMapping("editArticle")
    @ResponseBody
    public Result editArticle(@RequestBody HashMap<String,String> hashMap){
        String statusKey = hashMap.get("statusKey");
        Integer statusValue = Integer.parseInt(hashMap.get("statusValue"));
        Integer articleId = Integer.parseInt(hashMap.get("articleId"));

        Article.ArticleBuilder articleBuilder =  Article.builder().articleId(articleId);
        switch (statusKey){
            case "status" :
                articleBuilder.status(statusValue); break;
            case "isTop" :
                articleBuilder.isTop(statusValue); break;
            case "isComment" :
                articleBuilder.isComment(statusValue); break;
        }
        //入库修改
        Article article = articleBuilder.build();
        articleMapper.update(article);
        return Result.success();
    }


    @RequestMapping("deleteArticle")
    @ResponseBody
    public Result deleteArticle(@RequestBody HashMap<String,String> hashMap){
        String articleIds = hashMap.get("articleIds");
        List<String> articleIdList = Arrays.asList(articleIds.split(","));
        for (int i=0;i<articleIdList.size();i++) {
            Article article=articleMapper.queryById(Integer.parseInt(articleIdList.get(i)));
            if (article.getCoverImage() != null && !article.getCoverImage().contains(SystemConstant.BLANK_IMG)){
                Matcher m = SystemConstant.IMAGE_PATTERN.matcher(article.getCoverImage());
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
//            删除文章中的图片
//            Set<String> pics = new HashSet<>();
//            String img = "";
//
//            Matcher m_image;
//
//            m_image = SystemConstant.IMAGE_PATTERN.matcher(article.getArticleContextMd());
//            while (m_image.find()) {
//                // 得到<img />数据
//                img = m_image.group();
//                // 匹配<img>中的src数据
//                Matcher m = SystemConstant.IMAGE_PATTERN.matcher(img);
//                while (m.find()) {
//                    pics.add(m.group(0));
//                }
//                for (String imagePath: pics
//                     ) {
//                    Path path=Paths.get(imageFilePath + imagePath);
//                    try {
//                        Files.delete(path);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

        }

        articleMapper.deleteByArticleIds(articleIdList);
        return Result.success();
    }


    public static void main(String[] args) {
        List<Label> articleToLabelList = Lists.newArrayList();
        articleToLabelList.add(new Label(2,"测试"));
        articleToLabelList.add(new Label(3,"测试"));
        Integer  srcLabelId = 2;
        articleToLabelList = articleToLabelList.stream().filter(l -> {
            return  !l.getLabelId().equals(srcLabelId);
        }).collect(Collectors.toList());
        System.out.println(articleToLabelList.size());
    }


}
