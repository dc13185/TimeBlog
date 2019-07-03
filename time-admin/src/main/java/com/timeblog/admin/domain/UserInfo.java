package com.timeblog.admin.domain;

import com.timeblog.business.domain.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author: dong.chao
 * @create: 2019-07-03 19:00
 * @description: 后台用户表
 **/
@Data
public class UserInfo  implements UserDetails {

    private String sysUserId;

    private String userName;

    private String loginName;

    private String email;

    private String password;

    private String loginIp;

    private String loginDate;

    /**
     * 角色
     * */
    private String role;

    /**
     *  账户是否未过期,过期无法验证
     * */
    private boolean accountNonExpired = true;

    /**
     *  账户是否解锁，锁定的用户无法进行身份验证
     * */
    private boolean accountNonLocked = true;

    /**
     *  指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     * */
    private boolean credentialsNonExpired  = true;

    /**
     * 是否可用 ,禁用的用户不能身份验证
     * */
    private boolean enabled  = true;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }

    public String getUsername() {
        return this.loginName;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPassword() {
        return this.password;
    }


}
