package com.timeblog.spilder.controller;

import com.timeblog.business.domain.Sentence;
import com.timeblog.framework.mapper.SentenceDao;
import com.timeblog.framework.system.constant.SpiderConstant;
import com.timeblog.framework.system.constant.SystemConstant;
import com.timeblog.framework.system.utils.RedisUtils;
import com.timeblog.framework.system.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Date;


/**
 * @author Dong.Chao
 * @Classname SentenceController
 * @Description 抓取句子通句子
 * @Date 2020/3/9 11:01
 * @Version V1.0
 */

@Component("sentenceSpiderController")
public class SentenceSpiderController {

    @Autowired
    private SentenceDao sentenceDao;


    private  String hostUrl;

    /**
     *获取页数条件
     * */
    private boolean flag = true;



    public void spider(){
        hostUrl = "https://www.mingyantong.com/todayhot";
        //先清空
        SpiderConstant.SENTENCES.clear();
        spiderContent(hostUrl,1);
        //插入数据
        if (SpiderConstant.SENTENCES.size() > 0){
            //插入今天最火的句子
            sentenceDao.insertBatch(SpiderConstant.SENTENCES);
            //删除昨天的句子
            sentenceDao.deleteByCreateDate(LocalDate.now().minusDays(1L).toString());
        }
    }

    public void spiderContent(String anchorUrl,int count){
        try {
            String result = HttpClientUtils.doGetRequest(anchorUrl,null,null);
            Document document = Jsoup.parse(result);
            //获取页数
            if (flag){
                String page = document.getElementsByClass("pager-last").first().getElementsByTag("a").first().text();
                count = Integer.parseInt(page) - 1;
                flag = false;
            }
            Elements sentenceElements = document.getElementsByClass("views-field-phpcode");
            for (Element element : sentenceElements) {
                 Element authorElement =  element.getElementsByClass("views-field-field-oriwriter-value").first();
                 if (authorElement == null){
                     continue;
                 }
                 //作者
                 String author = authorElement.text();
                 //作品
                 String authorWork = "";
                 Element work =  element.getElementsByClass("views-field-field-oriarticle-value").first();
                 if (work !=null){
                     authorWork = work.text();
                 }
                Element  sentenceContentElement =  element.getElementsByClass("xlistju").first();
                String content = sentenceContentElement.text();
                if (content.length() > 350){
                    continue;
                }
                Sentence sentence = Sentence.builder().content(content).author(author).works(authorWork).createDate(new Date()).build();
                SpiderConstant.SENTENCES.add(sentence);
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        if (count>0){
            spiderContent(hostUrl+"?page="+count,--count);
        }

    }

}
