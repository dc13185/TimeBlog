package com.timeblog.admin.config.aop;

import com.timeblog.business.base.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dongchao
 * @Classname WebExceptionAspect
 * @Description 统一捕获异常
 * @Date 2020/2/14 21:03
 * @Version V1.0
 */

@Aspect
public class WebExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebExceptionAspect.class);

    /**
     * 连接点是@RequestMapping注解的方法
     * */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void webPointcut() {}

    @Around("webPointcut()")
    public Object handleThrowing(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            //直接返回
            return  proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
            //方法名
            String methodName = proceedingJoinPoint.getSignature().getName();
            //类名
            String className = proceedingJoinPoint.getTarget().toString();
            String errorStr = "类:"+className+",方法:"+methodName+",出现异常.异常信息为:"+throwable.getMessage();
            //自定义业务、入库操作 todo
            logger.error(errorStr,throwable);
            //异常返回指定信息
            Result result = Result.error(errorStr);
            return result;
        }
    }










}
