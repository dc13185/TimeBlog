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
     * 通过父ID查询单条数据
     *
     * @param typeId 主键
     * @return 实例对象
     */
    List<ArticleType> queryByParentId(Integer typeId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param pageDomain 实例对象
     * @return 对象列表
     */
    List<ArticleType> queryAll(PageDomain pageDomain);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param pageDomain 实例对象
     * @return 对象列表
     */
    List<ArticleType> queryAllTimeArticleTypes();

    /**
     * 新增
     * @param articleType
     */
    public void insert(ArticleType articleType);

    /**
     * 删除一行
     * @param articleTypeIds
     */
    public void deleteById(List articleTypeIds);


    /**
     * 删除一行
     * @param articleType
     */
    public void update(ArticleType articleType);


    /**
     * 查出最后一级节点类型
     *
     * @param
     * @return 对象列表
     */
    List<ArticleType> queryNotParentNode();


}
