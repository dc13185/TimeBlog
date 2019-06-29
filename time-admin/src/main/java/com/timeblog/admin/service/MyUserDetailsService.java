package com.timeblog.admin.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author: dong.chao
 * @create: 2019-06-28 17:07
 * @description:自定义UserDetailsService查询用户信息
 **/
public class MyUserDetailsService implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
