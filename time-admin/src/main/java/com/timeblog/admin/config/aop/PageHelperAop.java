package com.timeblog.admin.config.aop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.timeblog.business.base.Result;
import com.timeblog.business.domain.PageDomain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author: dong.chao
 * @create: 2019-09-07 15:30
 * @description: 以AOP实现分页
 **/



@Aspect
public class PageHelperAop {

    @Pointcut("execution(public * com.timeblog.admin.controller.*.*Controller.*WithPage(..))")
    public void serviceFindFunction(){}


    /** 
    * @Description: 使用AOP环绕通知进行分页
    * @Param: [proceedingJoinPoint] 
    * @return: java.lang.Object 
    * @Author: dong.chao
    * @Date: 2019/9/7 
    */ 
    @Around("serviceFindFunction()")
    public Object serviceImplAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //获取连接点方法运行时的入参列表
        Object[] args = proceedingJoinPoint.getArgs();

        //获取方法入参，必须为pageDomain
        PageDomain pageDomain = (PageDomain)args[0];

        //分页开始
        Page<Object> page = PageHelper.startPage(pageDomain.getPageNumber(),pageDomain.getPageSize());

        //引入方法开始执行,返回一定为Result
        Result result = (Result)proceedingJoinPoint.proceed();

        //再将分页总数放进去
        result.put("total",page.getTotal());

        //再进行返回
        return result;
    }


}
