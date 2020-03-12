package com.timeblog.business.domain;

import lombok.Data;

/**
 * @author Dong.Chao
 * @Classname QueryArticleEntiy
 * @Description 查询文章实体类
 * @Date 2020/3/12 1:34
 * @Version V1.0
 */
@Data
public class QueryArticleEntity extends PageDomain {

    private  Integer articleTypeId;
}
