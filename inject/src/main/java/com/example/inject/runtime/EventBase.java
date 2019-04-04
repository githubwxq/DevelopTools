package com.example.inject.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/04
 * desc:设计目的是 对所有的事件点击 进行扩展
 * version:1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)  //只能被用来标注“Annotation类型”。用来标注注解的注解
public @interface EventBase {
    /**
     * 设置监听的方法
     * @return
     */
    String listenerSetter();

    /**
     * 事件类型
     * @return
     */
    Class<?> listenerType();

    /**
     * 回调方法
     * 事件被触发后，执行回调方法名称 onclick(view)
     */
    String callBackMethod();
}



