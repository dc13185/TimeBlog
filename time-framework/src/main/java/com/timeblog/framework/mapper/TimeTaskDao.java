package com.timeblog.framework.mapper;


import com.timeblog.framework.task.entity.TimeTask;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TimeTask)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-10 12:25:56
 */
public interface TimeTaskDao {

    /**
     * 通过ID查询单条数据
     *
     * @param taskId 主键
     * @return 实例对象
     */
    TimeTask queryById(Integer taskId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TimeTask> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param timeTask 实例对象
     * @return 对象列表
     */
    List<TimeTask> queryAll(TimeTask timeTask);

    /**
     * 新增数据
     *
     * @param timeTask 实例对象
     * @return 影响行数
     */
    int insert(TimeTask timeTask);

    /**
     * 修改数据
     *
     * @param timeTask 实例对象
     * @return 影响行数
     */
    int update(TimeTask timeTask);

    /**
     * 通过主键删除数据
     *
     * @param taskId 主键
     * @return 影响行数
     */
    int deleteById(Integer taskId);

}
