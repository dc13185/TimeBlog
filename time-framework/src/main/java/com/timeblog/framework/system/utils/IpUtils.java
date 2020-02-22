package com.timeblog.framework.system.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dong.Chao
 * @Classname IpUtils
 * @Description
 * @Date 2020/2/21 14:01
 * @Version V1.0
 */
public class IpUtils {

    /**
     * @author: dongchao
     * @create: 2020/2/21-14:02
     * @description:获取项目路径
     * @param:
     * @return:
     */
    public static String getProjectPath(HttpServletRequest request){
        String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath();
        return url;
    }
}
