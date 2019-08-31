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

        return builder.sources(TimeBlogApplication.class);
    }
}
