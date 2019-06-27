package com.timeblog.framework.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: dong.chao
 * @create: 2019-06-27 10:21
 * @description: security配置
 **/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


   /** 通过authorizeRequests()定义哪些URL需要被保护、
       哪些不需要被保护。
       例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。

       通过formLogin()定义当需要用户登录时候，转到的登录页面。
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                 // 请求路径"/"，"/home"允许访问
            .antMatchers("/js/**","/css/**","/images/*","/fonts/**","/**/*.png","/**/*.jpg")
            .permitAll()
                //而其他的请求都需要认证
            .anyRequest()
            .authenticated()
        .and()
            .formLogin()
                // 定义登录的页面"/login"，允许访问
            .loginPage("/login/toLoginView")
            .permitAll()
        .and()
            .logout()
            .permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
        .withUser("user").password("password").roles("USER");
    }
}
