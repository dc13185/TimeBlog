package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

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

    private int status;

    private String commentQQ;

    private String commentMail;



}
