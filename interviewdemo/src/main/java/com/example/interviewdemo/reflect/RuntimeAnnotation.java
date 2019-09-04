package com.example.interviewdemo.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RuntimeAnnotation {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ClassInfo {
        String value();

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface FieldInfo {
        int[] value();

    }




    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MethodInfo  {
        String name() default "long";
        String data();
        int age() default 24;

    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface ParmeterInfo  {
        String value();

    }


// this.parameterAnnotationsArray = method.getParameterAnnotations();




}

//
//    /**
//     * 获取指定类型的注解
//     */
//    public <A extends Annotation> A getAnnotation(Class<A> annotationType);
//
//    /**
//     * 获取所有注解，如果有的话
//     */
//    public Annotation[] getAnnotations();
//
//    /**
//     * 获取所有注解，忽略继承的注解
//     */
//    public Annotation[] getDeclaredAnnotations();
//
//    /**
//     * 指定注解是否存在该元素上，如果有则返回true，否则false
//     */
//    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType);
//
//    /**
//     * 获取Method中参数的所有注解
//     */
//    public Annotation[][] getParameterAnnotations();  Annotation[][] annos = method.getParameterAnnotations();
//得到的结果是一个二维数组，因为参数前可以添加多个注解,,一个参数上不可以添加相同的注解,同一个注解可以加在不同的参数上
//例如:
//@RedisScan
//public void save(@RedisSave()int id,@RedisSave()String name){
//
//}
//1
//        第一个参数下标为0,第二个参数下标为1
//        也就是说:annos[0][0] = RedisSave
//        annos[1][0] = RedisSave


