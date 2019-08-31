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
        System.out.println("ooooo  o                 .oPYo. 8               \n" +
                "  8                      8   `8 8               \n" +
                "  8   o8 ooYoYo. .oPYo. o8YooP' 8 .oPYo. .oPYo. \n" +
                "  8    8 8' 8  8 8oooo8  8   `b 8 8    8 8    8 \n" +
                "  8    8 8  8  8 8.      8    8 8 8    8 8    8 \n" +
                "  8    8 8  8  8 `Yooo'  8oooP' 8 `YooP' `YooP8 \n" +
                "::..:::....:..:..:.....::......:..:.....::....8 \n" +
                ":::::::::::::::::::::::::::::::::::::::::::ooP'.\n" +
                ":::::::::::::::::::::::::::::::::::::::::::...::\n" +
                "------------------时光机启动成功-----------------");
    }
}
