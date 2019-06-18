package com.example.interviewdemo.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/23.
 */

public class DNBus {

    private Map<Class, List<SubscribeMethod>> METHOD_CACHE = new HashMap<>();

    private Map<String, List<Subscription>> SUBSCRIBES = new HashMap<>();

    private Map<Class, List<String>> REGISTER = new HashMap<>();

    private static volatile DNBus instance;

    private DNBus() {
    }

    public static DNBus getDefault() {
        if (null == instance) {
            synchronized (DNBus.class) {
                if (null == instance) {
                    instance = new DNBus();
                }
            }
        }
        return instance;
    }


    /**
     * 注册
     *
     * @param object
     */
    public void register(Object object) {
        Class<?> subscribeClass = object.getClass();
        //找到 对于类中所有被 Subscribe 什么的函数
        //将其 Method、Lable、执行函数需要的参数类型数组缓存
        List<SubscribeMethod> subscribes = findSubscribe(subscribeClass);

        //为了方便注销
        List<String> labels = REGISTER.get(subscribeClass);
        if (null == labels){
            labels = new ArrayList<>();
            REGISTER.put(subscribeClass,labels);
        }

        //执行表的制作
        for (SubscribeMethod subscribeMethod : subscribes) {
            //拿到标签
            String lable = subscribeMethod.getLable();
            if (!labels.contains(lable)){
                labels.add(lable);
            }

            //执行表数据是否存在
            List<Subscription> subscriptions = SUBSCRIBES.get(lable);
            if (null == subscriptions) {
                subscriptions = new ArrayList<>();
                SUBSCRIBES.put(lable, subscriptions);
            }
            subscriptions.add(new Subscription(subscribeMethod, object));
        }
    }

    private List<SubscribeMethod> findSubscribe(Class<?> subscribeClass) {
        //先看缓存中是否有
        List<SubscribeMethod> subscribeMethods = METHOD_CACHE.get(subscribeClass);
        //缓存中没有
        if (null == subscribeMethods) {
            subscribeMethods = new ArrayList<>();
            //获得methods
            Method[] methods = subscribeClass.getDeclaredMethods();
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (null != subscribe) {
                    String[] values = subscribe.value();
                    //这个函数的参数类型
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    for (String value : values) {
                        //设置权限
                        method.setAccessible(true);
                        subscribeMethods.add(new SubscribeMethod(value, method, parameterTypes));
                    }
                }
            }
            METHOD_CACHE.put(subscribeClass, subscribeMethods);
        }
        return subscribeMethods;
    }


    public void clear(){
        METHOD_CACHE.clear();
        SUBSCRIBES.clear();
        REGISTER.clear();
    }

    /**
     * 发送事件给订阅者
     *
     * @param lable
     * @param params
     */
    public void post(String lable, Object... params) {
        // params
        List<Subscription> subscriptions = SUBSCRIBES.get(lable);
        if (null == subscriptions) {
            return;
        }
        for (Subscription subscription : subscriptions) {
            SubscribeMethod subscribeMethod = subscription.getSubscribeMethod();
            //执行函数需要的参数类型数组
            Class[] paramterClass = subscribeMethod.getParamterClass();
            //真实的参数
            Object[] realParams = new Object[paramterClass.length];
            if (null != params) {
                for (int i = 0; i < paramterClass.length; i++) {
                    //传进来的参数 类型是method需要的类型
                    if (i < params.length && paramterClass[i].isInstance(params[i])) {
                        realParams[i] = params[i];
                    } else {
                        realParams[i] = null;
                    }
                }
            }
            try {
                subscribeMethod.getMethod().invoke(subscription.getSubscribe(), realParams);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反注册
     * @param object
     */
    public void unregister(Object object) {
        //对应对象类型的所有 注册的标签
        List<String> labels = REGISTER.get(object.getClass());
        if (null != labels){
            for (String label : labels) {
                //获得执行表中对应label的所有函数
                List<Subscription> subscriptions = SUBSCRIBES.get(label);
                if (null != subscriptions){
                    Iterator<Subscription> iterator = subscriptions.iterator();
                    while (iterator.hasNext()){
                        Subscription subscription = iterator.next();
                        //对象是同一个才删除
                        if (subscription.getSubscribe() == object){
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }
}
