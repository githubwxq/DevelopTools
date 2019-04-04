package com.example.inject.runtime;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by wxq
 */

public class ListenerInvocationHandler implements InvocationHandler{
    //activity   真实对象
    private Context context;
    private Map<String,Method> methodMap;

    public ListenerInvocationHandler(Context context, Map<String, Method> methodMap) {
        this.context = context;
        this.methodMap = methodMap;
    }

    //当代理对象被调用的时候会回调到这里 当点击textview 的售后 实际调用的是代理的onclick 会走invoke方法  里面判断
    //加了注解的方法直接 运行被代理类的方法 invoke 传递被代理 以及参数
    //Method中invoke（Object obj,Object...args）第一个参数为类的实例，第二个参数为相应函数中的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name=method.getName(); //代理类调用的方法
        System.out.print("ListenerInvocationHandler==============="+name);
        //决定是否需要进行代理
        Method metf=methodMap.get(name);
        if(metf!=null)
        {   //能找到 调用 actiivty中的的方法，任意方法名 当前代理对象代理了回调方法 代理了activiy中的方法  onclick1 onclick2 ，
            return  metf.invoke(context,args);
        }else
        {
            //未能找到 调用代理类的方法中的的方法， onclick1 onclick2 、、任意方法名
            return method.invoke(proxy,args);
        }
    }
}
