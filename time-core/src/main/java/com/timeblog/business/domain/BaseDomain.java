package com.timeblog.business.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author: dong.chao
 * @create: 2019-07-29 17:34
 * @description: 基础实体信息
 **/
@Data
public class BaseDomain {

    protected Date createTime;

    protected Date updateTime;
}
