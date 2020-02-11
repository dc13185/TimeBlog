package com.timeblog.admin.config.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: dong.chao
 * @create: 2019-06-19 18:45
 * @description: 设置默认访问路径、静态资源
 **/
@Configuration
public class DefaultViewConfig implements WebMvcConfigurer {

     @Value("${security.indexUrl}")
     private  String  indexUrl ;

     @Value("${imageFile.path}")
     private String imageFilePath;

    @Value("${imageFile.url}")
    private String imageUrl;

    /**
     * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/").setViewName("forward:" + indexUrl );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imageUrl).addResourceLocations("file:"+imageFilePath);
    }
}
