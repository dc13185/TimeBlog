import com.timeblog.spilder.config.HttpClientDownloaderLocal;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author: dong.chao
 * @create: 2019-11-04 14:36
 * @description: 测试爬虫项目
 **/
public class SpiderTestPageProcessor implements PageProcessor {

    private static String filePath = "D:\\抓取图片\\";

    private static HashMap<String,String> fileNames = new HashMap(1 >> 6);


    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");


    @Override
    public void process(Page page) {
        //进入一个分类,抓取所有详情页
        page.addTargetRequest(page.getHtml().links().regex("https://www.mzitu.com/xinggan/").get());
        if(page.getUrl().toString().equals("https://www.mzitu.com/xinggan/")){
            page.addTargetRequests(page.getHtml().regex("https://www.mzitu.com/[0-9]{1,7}").all());
        }
        //判断是否进入了详情页面
        if(page.getUrl().toString().matches("https://www.mzitu.com/[0-9]{1,7}")){
            //或者详情名字
            String name = page.getHtml().regex("<title>[\\w\\W]{1,100}</title> ").get().replaceAll("<title>","").replaceAll("</title>","").replaceAll("[:| |/|?|: |\"|>|<]","");
            fileNames.put(page.getUrl().toString(),name);
            //获取页面展示的页码
            List<String> imgPages = page.getHtml().regex("https://www.mzitu.com/[0-9]{1,7}/[0-9]{1,2}").all();
            Collections.sort(imgPages);
            //获取页码
            int startPage = 1;
            int endPage = Integer.parseInt(StringUtils.substringAfterLast(imgPages.get(imgPages.size()-1),"/"));
            //组织所有URL
            List<String> imgDateilUrl = Lists.list();
            for(int i = startPage; i<=endPage ; i++){
                imgDateilUrl.add(page.getUrl().toString()+"/"+i);
            }
            //所有链接再次加入待抓取列表
            page.addTargetRequests(imgDateilUrl);
        }
        //判断是否进入了页码列
        if(page.getUrl().toString().matches("https://www.mzitu.com/[0-9]{6}/[0-9]{1,2}")){
            //抓取到网页链接开始下载图片
            String imgUrl =  page.getHtml().regex("https://[\\s\\S]{1,50}/[0-9a-zA-Z]{1,10}/[0-9a-zA-Z]{1,4}/[0-9a-zA-Z]{1,15}.jpg").get();
            downloadByUrl(imgUrl,page.getUrl().toString());
        }

    }

    @Override
    public Site getSite() {
        return site;
    }


    /**
    * @Description: 下载图片
    * @Param: [urlstr]
    * @return: void
    * @Author: dong.chao
    * @Date: 2019/11/10
    */
    private static   void downloadByUrl(String urlstr,String originalUrl){
        String fileName  = "未获取到文件名";
        for (String o : fileNames.keySet()) {
            if(originalUrl.contains(o)){
                fileName = fileNames.get(o);
            }
        }
        URL url = null;
        try{
            url = new URL(urlstr);
            URLConnection con = url.openConnection();
            con.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");
            con.setRequestProperty("accept","image/webp,image/apng,image/*,*/*;q=0.8");
            con.setRequestProperty("referer",originalUrl);

            InputStream  inStream = con.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = inStream.read(buf)) != -1){
                outStream.write(buf,0,len);
            }
            inStream.close();
            outStream.close();
            //图片下载地址
            File file = new File(filePath+fileName);
            if(!file.exists()){
                file.mkdir();
            }
            //获取文件名
            String imgName = StringUtils.substringAfterLast(urlstr,"/");
            file = new File(filePath+fileName+"\\"+imgName);
            FileOutputStream op = new FileOutputStream(file);
            op.write(outStream.toByteArray());
            op.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void test(){


    }
    public static void main(String[] args) {
        String a = "https://i5.meizitu.net/2019/11/06a01.jpg";
        String b = "test";
        Spider.create(new SpiderTestPageProcessor()).setDownloader(new HttpClientDownloaderLocal()).addUrl("http://088ay.com/arttype/17.html").thread(5).run();
    }
}
