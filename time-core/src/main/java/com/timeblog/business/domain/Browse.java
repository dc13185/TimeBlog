package com.timeblog.business.domain;

import lombok.Data;

/**
 * @author: dong.chao
 * @create: 2019-07-29 18:40
 * @description: 浏览记录
 **/
@Data
public class Browse extends BaseDomain{

    private int browseId;

    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 浏览IP
     */
    private String  browseIp;

}
