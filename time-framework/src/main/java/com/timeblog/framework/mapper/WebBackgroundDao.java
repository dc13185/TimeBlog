package com.timeblog.framework.mapper;

import com.timeblog.business.domain.WebBackground;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (WebBackground)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-27 15:04:15
 */
public interface WebBackgroundDao {

    /**
     * 通过ID查询单条数据
     *
     * @param backgroundId 主键
     * @return 实例对象
     */
    WebBackground queryById(Integer backgroundId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WebBackground> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param WebBackground 实例对象
     * @return 对象列表
     */
    List<WebBackground> queryAll();

    /**
     * 新增数据
     *
     * @param WebBackground 实例对象
     * @return 影响行数
     */
    int insert(WebBackground WebBackground);

    /**
     * 修改数据
     *
     * @param WebBackground 实例对象
     * @return 影响行数
     */
    int update(WebBackground WebBackground);

    /**
     * 通过主键删除数据
     *
     * @param backgroundId 主键
     * @return 影响行数
     */
    int deleteById(Integer backgroundId);

}
