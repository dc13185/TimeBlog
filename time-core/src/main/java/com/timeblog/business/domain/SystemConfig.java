package com.timeblog.business.domain;

import lombok.Data;

/**
 * @author: dong.chao
 * @create: 2019-07-29 18:45
 * @description: 系统配置
 **/

@Data
public class SystemConfig extends BaseDomain {

    private int configId;

    /**
     * 首页描述
     */
    private String indexDesc;

    /**
     * 首页关键字
     */
    private String indexKeys;

    /**
     * 站长邮箱
     */
    private String authorEmail;

    /**
     * 站长QQ
     */
    private String QQ;
}
