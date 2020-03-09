package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (TimeComment)实体类
 *
 * @author makejava
 * @since 2020-03-05 21:33:50
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = -62697648647800009L;

    private Integer commentId;

    /**对应评论文章，如果为空，则为留言*/
    private Integer commentArticleId;

    /**父亲节点ID*/
    private Integer parentCommentId;

    /**回复ID*/
    private Integer replyId;

    /**头像*/
    private String commentPicture;
    /**评论内容*/
    private String commentContent;
    /**评论昵称*/
    private String commentNickname;
    /**评论IP*/
    private String commentIp;
    /**评论地址*/
    private String commentAddress;
    /**评论时间*/
    private Date createTime;


    /**评论状态*/
    private Integer commentStatus;

    private String commentQQ;

    private String commentMail;


    /**回复昵称*/
    private String replyName;

    private List<Comment> sonComments;

   /**评论文章*/
    private String commentArticleName;

}
