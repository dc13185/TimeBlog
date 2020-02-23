package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (TimeArticleLabel)实体类
 *
 * @author makejava
 * @since 2020-02-23 10:07:21
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLabel extends BaseDomain {

    private Integer timeArticleLabelId;
    /**文章类ID */
    private Integer articleId;
    /**标签id */
    private Integer labelId;

    private Date createDate;

}