package com.timeblog;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: dong.chao
 * @create: 2019-06-27 11:28
 * @description: 启用外部tomcat
 **/
public class TimeBlogServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TimeBlogApplication.class);
    }
}
