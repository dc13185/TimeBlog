package com.timeblog.admin.config.security;

import com.timeblog.admin.domain.UserInfo;
import com.timeblog.admin.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: dong.chao
 * @create: 2019-07-03 16:43
 * @description: 处理用户信息获取逻辑
 **/

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Resource
    private SysUserMapper sysUserMapper;



    /**
    * @Description:  处理用户名密码
    * @Param: [s]
    * @return: org.springframework.security.core.userdetails.UserDetails
    * @Author: dong.chao
    * @Date: 2019/7/3
    */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        logger.info("用户{}开始登录",loginName);
        try {
            UserInfo userInfo =  sysUserMapper.queryUserByLoginName(loginName);
            if (userInfo == null){
                return null;
            }
            return userInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }



}
