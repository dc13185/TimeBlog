package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author clw
 * @Classname Link
 * @Description 友链
 * @Date 2020/3/12 12:23
 * @Version V1.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    /**友链id*/
    private Integer linkId;

    /**友链名称*/
    private String linkName;
    /**友链链接*/
    private String linkHerf;
    /**友链头像*/
    private String linkImage;
    /**友链描述*/
    private String linkDescription;

    /**
     * 友链状态
     * 0为未审核
     * 1为审核通过
     * */
    private Integer linkStatus;
}