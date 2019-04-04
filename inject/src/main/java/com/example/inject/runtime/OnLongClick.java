package com.example.inject.runtime;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/04
 * desc:
 * version:1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerSetter = "setOnLongClickListener",
        listenerType = View.OnLongClickListener.class,callBackMethod = "onLongClick")
public @interface OnLongClick
{
    int[] value() default -1;
}
