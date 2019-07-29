package com.timeblog.business.domain;

import lombok.Data;

/**
 * @author: dong.chao
 * @create: 2019-07-29 18:36
 * @description: 文章标签
 **/
@Data
public class Label {

    private int labelId;

    private String labelName;

    /**
     * 标签描述
     */
    private String description;

}
