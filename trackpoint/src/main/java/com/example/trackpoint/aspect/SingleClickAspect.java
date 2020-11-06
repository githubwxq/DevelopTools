package com.example.trackpoint.aspect;

import com.example.trackpoint.annotation.NeedLogin;
import com.example.trackpoint.annotation.SingleClick;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/03
 * desc:
 * version:1.0
 */
@Aspect
public class SingleClickAspect {

    String TAG = "SingleClickAspect";

    long MIN_CLICK_DELAY_TIME = 5000;

    long lastClickTime = 0L;

    @Pointcut("execution(" +//执行语句
            "@com.example.trackpoint.annotation.SingleClick" +//注解筛选
            " * " + //类路径,*为任意路径
            "*" +   //方法名,*为任意方法名
            "(..)" +//方法参数,'..'为任意个任意类型参数
            ")")
    public void singleClick() {


    }

    @Around("singleClick()")
    public void aroundSingleClick(ProceedingJoinPoint joinPoint) throws Throwable{
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime-lastClickTime>MIN_CLICK_DELAY_TIME) {
            //将刚进入方法的时间赋值给上次点击时间
            lastClickTime = currentTime;
            //执行原方法
            joinPoint.proceed();
        }
    }




}
