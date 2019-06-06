package com.example.design_pattern.proxy.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynaProxyClass implements InvocationHandler {
    /**
     * 要处理的对象(也就是我们要在方法的前后加上业务逻辑的对象,如例子中的Hello)
     */
    private Object delegate;


    /**
     * 操作者
     */
    private Object operation ;

    /**
     * 动态生成方法被处理过后的对象 (写法固定)
     *
     * @param delegate
     * @return   (写法固定)
     */
    public Object bind(Object delegate,Object proxy) {
        this.operation=proxy;
        this.delegate = delegate;
        return Proxy.newProxyInstance(
                this.delegate.getClass().getClassLoader(), this.delegate
                        .getClass().getInterfaces(), this);
    }

    /**
     * 要处理的对象中的每个方法会被此方法送去JVM调用,也就是说,要处理的对象的方法只能通过此方法调用
     * 此方法是动态的,不是手动调用的
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            //反射获取操作者实列
            Class clazz=this.operation.getClass();
            Method start = clazz.getDeclaredMethod("start", new Class[]{Method.class});
            //反射执行start方法  把代理方法出发 的对应的method 传递给操作类
            start.invoke(this.operation,new Object[]{method });
            //约定大于重建
            result=method.invoke(this.delegate,args);
            //反射得到操作者的end方法
            Method end=clazz.getDeclaredMethod("end",new Class[] { Method.class });
            //反射执行end方法
            end.invoke(this.operation, new Object[] { method });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回方法返回值给调用者
        return result;
    }
}
