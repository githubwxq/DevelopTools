package com.wxq.commonlibrary.mydb.bean;
import com.wxq.commonlibrary.mydb.annotation.DbField;
import com.wxq.commonlibrary.mydb.annotation.DbTable;

/**
 * Created by wxq on 2018/3/7.
 */
//user表  字段：id  name  password   api.insert(new user());
@DbTable("tb_user")
public class DbUser {
    @DbField("_id")
    private Integer id;
    private String name;
    private String password;
    public DbUser(){}
    public DbUser(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
