package com.timeblog.framework.system.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author Dong.Chao
 * @Classname HttpClientUtils
 * @Description httpClient工具类
 * @Date 2020/3/6 11:02
 * @Version V1.0
 */
public class HttpClientUtils {


    /**
     * @author: dongchao
     * @create: 2020/3/6-11:04
     * @description: get请求，请求不为null则请求成功
     * @param:
     * @return:
     */
    public static String getRequest(String getUrl) throws IOException {
        HttpGet httpGet = new HttpGet(getUrl);
        //获取当前客户端对象
        HttpClient httpClient = HttpClientBuilder.create().build();
        //通过请求获取相应对象
        HttpResponse response = httpClient.execute(httpGet);
        // 判断网络连接状态码是否正常(0--200都数正常)
        String result=null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result= EntityUtils.toString(response.getEntity(),"GBK");
        }
        return result;
    }
}
