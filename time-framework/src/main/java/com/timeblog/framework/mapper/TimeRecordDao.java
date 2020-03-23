package com.timeblog.framework.mapper;

import com.timeblog.business.domain.TimeRecord;
import com.timeblog.business.domain.dto.StatisticsDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (TimeRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-17 16:51:32
 */
public interface TimeRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    TimeRecord queryById(Integer recordId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TimeRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 查询指定行数据
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 对象列表
     */
    List<TimeRecord> queryAllByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param timeRecord 实例对象
     * @return 对象列表
     */
    List<TimeRecord> queryAll(TimeRecord timeRecord);


    /**
     * 查询未完成时间
     *
     * @param nowDate 当前时间
     * @return 对象列表
     */
    List<TimeRecord> queryTodoRecord(Date nowDate);



    /**
     * 新增数据
     *
     * @param timeRecord 实例对象
     * @return 影响行数
     */
    int insert(TimeRecord timeRecord);

    /**
     * 修改数据
     *
     * @param timeRecord 实例对象
     * @return 影响行数
     */
    int update(TimeRecord timeRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 影响行数
     */
    int deleteById(Integer recordId);


    /**
     * 统计统计情况 （每个事件连续天数）
     *
     * @param
     * @return
     */
    List<StatisticsDto> statisticsData(Integer eventType);

}
