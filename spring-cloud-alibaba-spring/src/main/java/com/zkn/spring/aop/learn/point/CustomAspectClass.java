package com.zkn.spring.aop.learn.point;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author conanzhang@木森
 * @description 定义一个切面
 * @date 3/12/21 1:32 PM
 * @classname CustomAspectClass
 */
@Aspect
@Component
public class CustomAspectClass {

    @Pointcut("execution(* com.zkn.spring.aop.learn.controller..*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("开始环绕通知～～～");
        Object obj = proceedingJoinPoint.proceed();
        System.out.println("结束环绕通知～～～");
        return obj;
    }
}
