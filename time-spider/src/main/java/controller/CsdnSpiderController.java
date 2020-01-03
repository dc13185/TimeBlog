package controller;

import config.HttpClientDownloaderLocal;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * @author: dong.chao
 * @create: 2019-11-14 14:38
 * @description: CSDN抓取控制器
 **/
public class CsdnSpiderController implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");

    @Override
    public void process(Page page) {
        String a = page.getHtml().toString();

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CsdnSpiderController()).setDownloader(new HttpClientDownloaderLocal()).addUrl("https://www.csdn.net/nav/java").thread(5).run();
    }
}
