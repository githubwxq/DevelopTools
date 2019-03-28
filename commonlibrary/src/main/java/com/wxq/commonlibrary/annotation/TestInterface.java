package com.wxq.commonlibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/27
 * desc:测试  运行时获取信息  通过反射获取field变量
 * version:1.0
 */
@Target(ElementType.FIELD)  // 描述域
@Retention(RetentionPolicy.RUNTIME) // 运行时间
public @interface TestInterface {
    int value() default 100;
}
