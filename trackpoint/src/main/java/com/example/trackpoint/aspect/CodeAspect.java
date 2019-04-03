package com.example.trackpoint.aspect;

import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/03
 * desc:
 * version:1.0
 */
@Aspect
public class CodeAspect {

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onViewClickAop(final ProceedingJoinPoint joinPoint) throws Throwable{
        joinPoint.proceed();
        Object target = joinPoint.getTarget().getClass().getName();
        System.out.println("调用者+"+target);
        View view=(View) joinPoint.getArgs()[0];
        String xmlId = "";
        if (view.getId() != View.NO_ID) {
            xmlId = view.getResources().getResourceEntryName(view.getId());
        }
        System.out.println("xmlId+"+xmlId);
    }

}
