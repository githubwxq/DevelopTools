package com.example.interviewdemo.bus;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/23.
 */

public class SubscribeMethod {

    private String lable;
    //方法
    private Method method;
    //参数的类型
    private Class[] paramterClass;

    public SubscribeMethod(String lable, Method method, Class[] paramterClass) {
        this.lable = lable;
        this.method = method;
        this.paramterClass = paramterClass;
    }

    public String getLable() {
        return lable;
    }

    public Method getMethod() {
        return method;
    }

    public Class[] getParamterClass() {
        return paramterClass;
    }
}
