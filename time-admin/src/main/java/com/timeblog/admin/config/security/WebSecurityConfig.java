package com.timeblog.admin.config.security;

import com.timeblog.admin.config.constant.SystemConstant;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author: dong.chao
 * @create: 2019-06-27 10:21
 * @description: springSecurity 配置类
 **/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private  Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailHander;

    @Value("${security.staticUrl}")
    private String staticUrl;

   /** 通过authorizeRequests()定义哪些URL需要被保护、
       哪些不需要被保护。
       例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。

       通过formLogin()定义当需要用户登录时候，转到的登录页面。
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String [] staticUrls = staticUrl.split(",");
        http
            .authorizeRequests()
                 // 请求路径"/"，"/home"允许访问
            .antMatchers(staticUrls)
            .permitAll()
                //而其他的请求都需要认证
            .anyRequest()
            .authenticated()
        .and()
            .formLogin()
                // 定义登录的页面"/login"，允许访问
            .loginPage("/login/toLoginView")
                //登录请求拦截的url,也就是form表单提交时指定的action
            .loginProcessingUrl("/login/from")
            .successHandler(myAuthenticationSuccessHandler)
            .failureHandler(myAuthenticationFailHander)
                //登录失败跳转页面   .failureForwardUrl("/login/loginFail")
            .permitAll()
                //设置frame在同一个域名下可以访问
        .and().headers().frameOptions().sameOrigin()
        .and().logout().permitAll()
        .and().csrf().ignoringAntMatchers(SystemConstant.CRSF_CLOSE_URL);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(myAuthenticationProvider);
    }






}
