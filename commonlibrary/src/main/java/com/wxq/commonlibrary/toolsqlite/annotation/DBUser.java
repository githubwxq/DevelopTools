package com.wxq.commonlibrary.toolsqlite.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置用户目录
 * ElementType.TYPE:用在类上;
 * RetentionPolicy.RUNTIME:运行时,生命周期最长;
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBUser {
    //默认方法
    String value();
}
