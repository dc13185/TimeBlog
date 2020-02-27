package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (TimeWebConfig)实体类
 *
 * @author dong.chao
 * @since 2020-02-27 10:55:08
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BlogWebConfig implements Serializable {
    private static final long serialVersionUID = 558354802902516476L;
    /** time-blog标题*/
    private String blogTitle;
    /** time-blog名称*/
    private String blogName;
    /** 博客风格*/
    private String blogStyle;
    /** 博客创建时间*/
    private Date blogCreateTime;
    /** 作者邮箱*/
    private String blogAuthorMail;
    /** 作者QQ*/
    private String blogAuthorQq;
    /** 作者微信*/
    private String blogAuthorWx;
    /** 置顶博客类型*/
    private Integer indexTopArticleType;
    /** 首页背景图*/
    private String indexBackground;
}
