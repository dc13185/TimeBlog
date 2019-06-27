package com.timeblog.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: dong.chao
 * @create: 2019-06-19 18:45
 * @description: 设置默认访问路径
 **/
@Configuration
public class DefaultViewConfig implements WebMvcConfigurer {

     private static String  indexUrl = "/index";

    /**
     * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
     */

    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/").setViewName("forward:" + indexUrl );
    }



}
