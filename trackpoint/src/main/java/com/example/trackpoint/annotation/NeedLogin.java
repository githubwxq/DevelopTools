package com.example.trackpoint.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/03
 * @Target：表示该注解可以用于什么地方，可能的ElementType参数有：
 * CONSTRUCTOR：构造器的声明
 * FIELD：域声明（包括enum实例）
 * LOCAL_VARIABLE：局部变量声明
 * METHOD：方法声明
 * PACKAGE：包声明
 * PARAMETER：参数声明
 * TYPE：类、接口（包括注解类型）或enum声明
 * @Retention
 * 表示需要在什么级别保存该注解信息。可选的RetentionPolicy参数包括：
 * SOURCE：注解将被编译器丢弃
 * CLASS：注解在class文件中可用，但会被VM丢弃
 * RUNTIME：VM将在运行期间保留注解，因此可以通过反射机制读取注解的信息
 * version:1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {
    /**
     * 展示Dialog提示登录
     */
    int SHOW_DIALOG = 0;

    /**
     * 弹出吐司提示登录
     */
    int SHOW_TOAST = 1;

    /**
     * 没有响应
     */
    int NO_RESPONSE = 2;


    //只能定义一个东西：注解类型元素（annotation type element）。咱们来看看其语法：  注解类型元素！

    //注解类型元素！
    int tipeType() default  SHOW_TOAST;

    /**
     * 登录Activity
     */
    Class<?> loginActivity();

    String tipToast() default "当前尚未登录，请先登录";

    String tipDialog() default "当前尚未登录，是否前往登录？";


}


//第一步，定义注解——相当于定义标记；
//第二步，配置注解——把标记打在需要用到的程序代码中；
//第三步，解析注解——在编译期或运行时检测到标记，并进行特殊操作。