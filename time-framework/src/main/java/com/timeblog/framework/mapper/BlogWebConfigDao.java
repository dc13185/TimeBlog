package com.timeblog.framework.mapper;

import com.timeblog.business.domain.BlogWebConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (BlogWebConfig)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-27 10:57:03
 */
public interface BlogWebConfigDao {

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<BlogWebConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param BlogWebConfig 实例对象
     * @return 对象列表
     */
    BlogWebConfig queryAll();

    /**
     * 新增数据
     *
     * @param BlogWebConfig 实例对象
     * @return 影响行数
     */
    int insert(BlogWebConfig BlogWebConfig);

    /**
     * 修改数据
     *
     * @param BlogWebConfig 实例对象
     * @return 影响行数
     */
    int update(BlogWebConfig BlogWebConfig);

}
