package com.wxq.commonlibrary.toolsqlite.test;


import com.wxq.commonlibrary.toolsqlite.annotation.DBTable;

//设置表名
@DBTable("tb_person")
public class BeanPerson {
    public String name;
    public Integer age;

    public BeanPerson() {
    }

    public BeanPerson(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "BeanPerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
