package com.timeblog.framework.mapper;

import com.timeblog.business.domain.ArticleLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (ArticleLabel)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-23 10:10:46
 */
public interface ArticleLabelMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param articleLabelId 主键
     * @return 实例对象
     */
    ArticleLabel queryById(Integer articleLabelId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ArticleLabel> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param articleLabel 实例对象
     * @return 对象列表
     */
    List<ArticleLabel> queryAll(ArticleLabel articleLabel);

    /**
     * 新增数据
     *
     * @param articleLabel 实例对象
     * @return 影响行数
     */
    int insert(ArticleLabel articleLabel);

    /**
     * 修改数据
     *
     * @param articleLabel 实例对象
     * @return 影响行数
     */
    int update(ArticleLabel articleLabel);

    /**
     * 通过主键删除数据
     *
     * @param articleLabelId 主键
     * @return 影响行数
     */
    int deleteById(Integer articleLabelId);


    /**
     * 通过主键删除数据
     *
     * @param labelIds 文章类型
     * @return 影响行数
     */
    int deleteByArticleIds(List labelIds);


}