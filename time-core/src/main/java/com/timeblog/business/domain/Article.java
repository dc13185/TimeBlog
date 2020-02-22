package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    private int articleId;

    /** 文章标题 */
    private String articleTitle;

    /** 文章内容html */
    private String articleContextHtml;

    /** 文章内容md */
    private String articleContextMd;

    /** 文章图片 */
    private String coverImage;

    /** 是否开启评论 1：开启  0：不开启 */
    private int isComment;

    /** 是否原创 1：是  0：否  -1:不显示 */
    private int isOriginal;

    /** 是否原创 0:置顶 1：非置顶   */
    private int isTop;

    /** -1:删除 0:草稿类容 1:正式发布 */
    private int status;

    /** 关键字 */
    private String keywords;

    /** 分类ID */
    private int articleTypeId;






}
