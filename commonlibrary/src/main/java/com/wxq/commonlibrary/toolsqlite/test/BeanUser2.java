package com.wxq.commonlibrary.toolsqlite.test;


import com.wxq.commonlibrary.toolsqlite.annotation.DBField;
import com.wxq.commonlibrary.toolsqlite.annotation.DBTable;

/**
 * 用户
 */
@DBTable("tb_user")
public class BeanUser2 {
    @DBField("id4")
    private String id;
    private String name;
    private String age;
    private String password;

    public BeanUser2() {
    }

    public BeanUser2(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "BeanUser2{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", password='" + password + '\'' +
                '}' + "\n";
    }
}
