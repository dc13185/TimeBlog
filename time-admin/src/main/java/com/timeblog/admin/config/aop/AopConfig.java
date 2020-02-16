package com.timeblog.admin.config.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: dong.chao
 * @create: 2019-09-07 16:36
 * @description: AOP配置类
 **/

@EnableAspectJAutoProxy
@Configuration
public class AopConfig {

    @Bean
    public PageHelperAop pageHelperAop(){
        return new PageHelperAop();
    }

    @Bean
    public WebExceptionAspect webExceptionAspect(){return new WebExceptionAspect();}


}
