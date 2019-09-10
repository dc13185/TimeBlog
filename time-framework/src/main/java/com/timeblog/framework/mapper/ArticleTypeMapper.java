package com.timeblog.framework.mapper;


import com.timeblog.business.domain.ArticleType;
import com.timeblog.business.domain.PageDomain;

import java.util.List;

/**
 * @author: dong.chao
 * @create: 2019-08-29 17:07
 * @description: 文章类型接口
 **/
public interface ArticleTypeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param typeId 主键
     * @return 实例对象
     */
    ArticleType queryById(Integer typeId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param ArticleType 实例对象
     * @return 对象列表
     */
    List<ArticleType> queryAll(PageDomain pageDomain);

}
