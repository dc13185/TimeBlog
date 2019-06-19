package com.timeblog.business.domain;

import lombok.Data;

/**
 * @author: dong.chao
 * @create: 2019-06-19 19:10
 * @description: 系统用户
 **/

@Data
public class SysUser {

    private String sysUserId;

    private String userName;

    private String loginName;

    private String email;

    private String password;

    private String loginIp;

    private String loginDate;


}
