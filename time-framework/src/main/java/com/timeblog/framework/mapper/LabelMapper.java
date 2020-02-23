package com.timeblog.framework.mapper;

import com.timeblog.business.domain.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Label)表数据库访问层
 *
 * @author dongchao
 * @since 2020-02-23 10:04:42
 */
public interface LabelMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param labelId 主键
     * @return 实例对象
     */
    Label queryById(Integer labelId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Label> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param Label 实例对象
     * @return 对象列表
     */
    List<Label> queryAll(Label Label);


    /**
     * 通过文章id查出所有标签
     *
     * @param articleId 文章ID
     * @return 对象列表
     */
    List<Label> queryLabelsByArticleId(Integer articleId);


    /**
     * 新增数据
     *
     * @param Label 实例对象
     * @return 影响行数
     */
    int insert(Label Label);

    /**
     * 修改数据
     *
     * @param Label 实例对象
     * @return 影响行数
     */
    int update(Label Label);

    /**
     * 通过主键删除数据
     *
     * @param labelId 主键
     * @return 影响行数
     */
    int deleteById(Integer labelId);

}