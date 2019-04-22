package com.wxq.commonlibrary.toolsqlite.test;


import com.wxq.commonlibrary.toolsqlite.annotation.DBField;
import com.wxq.commonlibrary.toolsqlite.annotation.DBTable;

/**
 * 用户
 */
//表名
@DBTable("tb_user")
public class BeanUser {
    @DBField("id3")
    private String id;
    private String name;
    private String password;

    public BeanUser() {
    }

    public BeanUser(String id, String name, String password) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "BeanUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}' + "\n";
    }
}
