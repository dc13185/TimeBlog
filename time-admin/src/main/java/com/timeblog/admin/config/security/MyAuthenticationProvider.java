package com.timeblog.admin.config.security;


import com.timeblog.admin.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author: dong.chao
 * @create: 2019-07-03 19:43
 * @description: 密码校验
 **/
@Service
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsService myUserDetailService;

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        // 这个获取表单输入中返回的用户名;
        String userName = authentication.getName();
        // 这个是表单中输入的密码；
        String password = (String) authentication.getCredentials();
        // 这里构建来判断用户是否存在和密码是否正确
        // 这里调用我们的自己写的获取用户的方法；
        UserInfo userInfo = (UserInfo) myUserDetailService.loadUserByUsername(userName);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // //这里我们还要判断密码是否正确，实际应用中，我们的密码一般都会加密，以Md5加密为例
        // Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
        // //这里第个参数，是salt
        // 就是加点盐的意思，这样的好处就是用户的密码如果都是123456，由于盐的不同，密码也是不一样的，就不用怕相同密码泄漏之后，不会批量被破解。
        // String encodePwd=md5PasswordEncoder.encodePassword(password, userName);
        // //这里判断密码正确与否
        // if(!userInfo.getPassword().equals(encodePwd))
        // {
        // throw new BadCredentialsException("密码不正确");
        // }
        // //这里还可以加一些其他信息的判断，比如用户账号已停用等判断，这里为了方便我接下去的判断，我就不用加密了。
        //
        if (!userInfo.getPassword().equals(password)) {
            throw new BadCredentialsException("用户名或密码不正确");
        }
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);

    }



}
