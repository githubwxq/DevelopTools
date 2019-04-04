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
@EventBase(listenerSetter = "setOnClickListener",listenerType= View.OnClickListener.class,callBackMethod="onClick")
public @interface OnClick {
    /**
     * 哪些kongji控件id 进行设置点击事件
     * @return
     */
    int[] value();
}
