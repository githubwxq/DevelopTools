package com.example.design_pattern.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/02
 * desc:动态代理实现
 * Java提供了动态的代理接口InvocationHandler，实现该接口需要重写invoke()方法：
 *
 * version:1.0
 */
public class DynamicProxy implements InvocationHandler {
    private Object obj;//被代理的对象
    public DynamicProxy(Object obj) {
        this.obj = obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        System.out.println("invoke diao yong： "+method);
//        for (Object object : objects) {
//            System.out.println("invoke diao yong： "+object.toString());
//        }
        Object invoke = method.invoke(obj, objects);
        return invoke;
    }
}
