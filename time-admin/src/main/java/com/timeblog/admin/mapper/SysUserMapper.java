package com.timeblog.admin.mapper;

import com.timeblog.business.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (TimeSystemUser)表数据库访问层
 *
 * @author makejava
 * @since 2019-06-28 16:45:56
 */
public interface SysUserMapper {

    /**
     * 通过ID查询单条数据
     *TimeSystemUser
     * @param sysUserId 主键
     * @return 实例对象
     */
    SysUser queryUser(SysUser sysUserId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param timeSystemUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser timeSystemUser);

    /**
     * 新增数据
     *
     * @param timeSystemUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser timeSystemUser);

    /**
     * 修改数据
     *
     * @param timeSystemUser 实例对象
     * @return 影响行数
     */
    int update(SysUser timeSystemUser);

    /**
     * 通过主键删除数据
     *
     * @param sysUserId 主键
     * @return 影响行数
     */
    int deleteById(Integer sysUserId);

}