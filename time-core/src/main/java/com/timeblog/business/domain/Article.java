package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * @author: dong.chao
 * @create: 2019-07-29 10:35
 * @description: 文章
 **/
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseDomain  {

    private Integer articleId;

    /** 文章标题 */
    private String articleTitle;

    /** 文章内容html */
    private String articleContextHtml;

    /** 文章内容md */
    private String articleContextMd;

    /** 文章图片 */
    private String coverImage;
    /** 是否开启评论 1：开启  0：不开启 */
    private Integer isComment;

    /** 是否原创 1：是  0：否  -1:不显示 */
    private Integer isOriginal;

    /** 是否原创 0:不指定 1：指定   */
    private Integer isTop;

    /** -1:删除 0:草稿类容 1:正式发布 */
    private Integer status;

    /** 分类ID */
    private Integer articleTypeId;

    /** 标签id */
    private String labelIds;


    /** 简述 */
    private String articleIntroduction;


    /** 改文章的标签 */
    private List<Label> labelList;

    /** 浏览量 */
    private Integer accessCount;

    /** 点赞量 */
    private Integer likeCount;



}
