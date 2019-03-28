package com.wxq.commonlibrary.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/27
 * desc: 练习注解  RUNTIME 运行时 处理 通过反射
 * version:1.0
 */

@Documented
@Target(ElementType.TYPE)  // 描述类何接口的
@Retention(RetentionPolicy.RUNTIME) // 生命周期   class编译时运行速度  source  runtime 运行时有效通过反射获取注解内容
@Inherited //可继承的
public @interface metaTest {
    public  String doTest();
}
