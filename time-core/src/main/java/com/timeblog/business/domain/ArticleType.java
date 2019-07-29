package com.timeblog.business.domain;

import lombok.Data;

/**
 * @author: dong.chao
 * @create: 2019-07-29 17:26
 * @description: 文章类型
 **/
@Data
public class ArticleType extends BaseDomain {

    private String typeId;

    /** 类型名 */
    private String typeName;

    /** 排序 */
    private int sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否可用 1：可用   0：不可用
     */
    private int isAvailable;

    /**
     * 描述
     */
    private String description;


}
