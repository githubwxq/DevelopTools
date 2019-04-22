package com.wxq.commonlibrary.toolsqlite.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/22
 * desc:
 * version:1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBField {
    //默认方法
    String value();

}
