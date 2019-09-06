package com.example.interviewdemo.genericity;

import java.lang.reflect.ParameterizedType;

public class RealType<T> {

    private Class<T> clazz;

    public Class getRealType() {
//     getGenericSuperclass   返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        return clazz;
    }

    public static void main(String[] args) {
        RealType<String> type=new RealType<>();

        Class realType = type.getRealType();
        System.out.println("realtype"+realType.getName());


    }




}
