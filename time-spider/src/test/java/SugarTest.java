import com.timeblog.spilder.config.HttpClientDownloaderLocal;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Dong.Chao
 * @Classname SugarTest
 * @Description 新功能体验
 * @Date 2020/3/10 1:20
 * @Version V1.0
 */
public class SugarTest  implements PageProcessor {

    //文件地址
    private static String filePath = "D:\\images\\";

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");


    @Override
    public void process(Page page) {
        if (page.getUrl().toString().contains("arttype")){
            List<String> urls = page.getHtml().links().regex("/artdetail/[0-9]{1,7}.html").all();
            Collections.shuffle(urls);
            page.addTargetRequests(urls);
        }
        //到详情里面了
        if(page.getUrl().toString().contains("artdetail")){
            List<String> dowUrls = page.getHtml().regex("https://[\\s\\S]{1,100}.jpg").all();
            downloadByUrl(dowUrls);
        }

    }


    /**
     * @Description: 开始
     * @Param: [urlstr]
     * @return: void
     * @Author: dong.chao
     * @Date: 2019/11/10
     */
    private static void downloadByUrl(List<String> urls){
        for (String urlStr : urls) {
            String fileName = UUID.randomUUID().toString().replaceAll("-","") +".jpg";
            try{
                URL url  = new URL(urlStr);
                URLConnection con = url.openConnection();
                con.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");
                con.setRequestProperty("accept","image/webp,image/apng,image/*,*/*;q=0.8");
                InputStream inStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while((len = inStream.read(buf)) != -1){
                    outStream.write(buf,0,len);
                }
                inStream.close();
                outStream.close();
                File file = new File(filePath);
                if(!file.exists()){
                    file.mkdir();
                }
                file = new File(filePath+fileName);
                FileOutputStream op = new FileOutputStream(file);
                op.write(outStream.toByteArray());
                op.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SugarTest()).setDownloader(new HttpClientDownloaderLocal()).addUrl("http://088ay.com/arttype/18.html").thread(1).run();

    }
}
