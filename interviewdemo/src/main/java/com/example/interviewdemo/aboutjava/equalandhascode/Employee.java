package com.example.interviewdemo.aboutjava.equalandhascode;

import java.util.Objects;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/31
 * desc:
 * version:1.0
 */
public class Employee {
    private String name;
    private double salaary;
    private  String id;


    @Override
    public boolean equals( Object obj) {

        if (this==obj){

            return  true;
        }

        if (obj==null) {
            return  false;
        }

        if (getClass()!=obj.getClass()) {
            return false;
        }

        // 类型相同, 比较内容是否相同
        Employee other = (Employee) obj;

        return Objects.equals(name,other.name);

    }
}
