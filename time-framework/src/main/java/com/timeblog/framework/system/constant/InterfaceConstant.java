package com.timeblog.framework.system.constant;

import java.security.MessageDigest;

/**
 * @author Dong.Chao
 * @Classname InterfaceConstant
 * @Description 接口的一些配置
 * @Date 2020/3/5 21:53
 * @Version V1.0
 */
public class InterfaceConstant {

    /**
     * 根据QQ获取头像的接口
     * */
    public static String  GET_QQ_PICTURE_INTERFACE = "http://q1.qlogo.cn/g?b=qq&nk=%s&s=100&t=";



    /**
     * 根据QQ获取昵称的接口
     * */
    public static String  GET_QQ_NICKNAME_INTERFACE = "https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins=%s";


    /**
     * 根据IP获取地址的接口
     * */
    public static String  GET_IP_ADDRESS_INTERFACE = "https://apis.map.qq.com/ws/location/v1/ip?ip=";

    /**
     * 腾讯定位Key
     * */
    public static String APP_KEY = "RR5BZ-Z5K6F-VYPJ4-JAYDX-75GHZ-CNBVG";


    /**
     * 腾讯定位Secret_key
     * */
    public static String SECRET_KEY = "TPPwVuVtnMNst5WDPNOhcnRLfXTRj18";


    public static void main(String[] args) {
        String ceshi = "/ws/location/v1/ip?ip=183.95.249.196&key="+APP_KEY+SECRET_KEY;
        /*  String sing = encode(ceshi);
        System.out.println(encode(ceshi));

        String jiami = "https://apis.map.qq.com/ws/location/v1/ip?ip=183.95.249.196&key="+APP_KEY+"&sig="+sing;
        System.out.println(jiami);
        */

    }






}
