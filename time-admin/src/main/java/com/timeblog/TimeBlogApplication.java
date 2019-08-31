package com.timeblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: dong.chao
 * @create: 2019-06-17 11:03
 * @description: SpringBoot启动
 * (exclude = DataSourceAutoConfiguration.class)
 **/

@SpringBootApplication
@ComponentScan(value = {"com.timeblog.*.**"})
@MapperScan("com.timeblog.*.mapper")
public class TimeBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimeBlogApplication.class, args);
    }
}
