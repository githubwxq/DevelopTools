package com.wxq.commonlibrary.eventbus;

import java.lang.reflect.Method;

/**
 *订阅的事件和方法.
 * 1：处理事件线程。ThreadMode
 * 2：处理事件订阅方法类
 * 3：Method 处理事件方法名称
 * ！！！！！！！！！！事件的分发！！！！！！！！！！！！
 */
public class SubscribleMethod {
    Method method;
    private ThreadMode threadMode;
    private Class<?>aClassType;

    public SubscribleMethod(Method method, ThreadMode threadMode, Class<?> aClassType) {
        this.method = method;
        this.threadMode = threadMode;
        this.aClassType = aClassType;
    }
    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public Class<?> getaClassType() {
        return aClassType;
    }
}
