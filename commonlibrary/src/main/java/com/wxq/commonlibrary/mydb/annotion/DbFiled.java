package com.wxq.commonlibrary.mydb.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/08
 * desc:数据库自定义表明 放在注解里面对应数据库表
 * version:1.0
 */
@Target(ElementType.FIELD) //注解修饰在属性上面
@Retention(RetentionPolicy.RUNTIME) //运行时注解
public @interface DbFiled {
    String value();
}
