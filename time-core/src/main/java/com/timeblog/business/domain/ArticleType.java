package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: dong.chao
 * @create: 2019-07-29 17:26
 * @description: 文章类型
 **/
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleType extends BaseDomain  {

    private String typeId;

    private String parentId;

    /** 类型名 */
    private String typeName;

    /** 排序 */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否可用 1：可用   0：不可用
     */
    private Integer isAvailable;

    /**
     * 描述
     */
    private String description;

    /**
     * 改类型对应文章数
     */

    private Integer articleNum;

    /**
     * 对应子类型
     */
    private List<ArticleType> sonArticleTypes;





}
