package com.timeblog.admin.mapper;

import com.timeblog.admin.domain.UserInfo;
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
     * @param loginName 登录用户名
     * @return 实例对象
     */
    UserInfo queryUserByLoginName(String loginName);


}