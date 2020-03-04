package com.timeblog.admin.controller;

import com.timeblog.admin.mapper.SysUserMapper;
import com.timeblog.business.base.BaseController;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author: dong.chao
 * @create: 2019-06-25 19:27
 * @description: 登录Controller
 **/
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
    * @Description:   前往登录页面
    * @Param: []
    * @return: java.lang.String
    * @Author: dong.chao
    * @Date: 2019/6/25
    */
    @RequestMapping("/toLoginView")
    public String toLoginView(Authentication authentication){
        if (authentication == null ){
            return  "login";
        }else{
            return  "forward:index/toIndexView";
        }
    }



    @RequestMapping("/toLogin")
    @ResponseBody
    public String login(SysUser paramSysUser){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "";
    }




    @RequestMapping("/loginFail")
    public String loginFail(HttpServletRequest request){
       Result result = new Result();
       result.put("code",500);
       request.setAttribute("result",result);
       return  "login";
    }



}
