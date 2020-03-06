package com.timeblog.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dong.Chao
 * @Classname BlogUser
 * @Description 登录进来的用户
 * @Date 2020/3/5 21:47
 * @Version V1.0
 */

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BlogUser {

    /** 可能为QQ号微信号*/
    public String userId;

    public String qq;

    public String wx;

    /** 头像 */
    public String picture;

    public String nickname;

    public String address;

    /** 设备*/
    public String device;

    public String ip;

}
