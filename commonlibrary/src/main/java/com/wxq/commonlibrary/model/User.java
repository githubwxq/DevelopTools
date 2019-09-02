package com.wxq.commonlibrary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/23
 * desc:用于测试的用户对象
 * version:1.0
 */
@Entity
public class User {


    /**
     * name : 王晓清
     */
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String phone;
    public String pwd;
    public String uid;
    public String heardPic;
    public String age;
    @Generated(hash = 74982852)
    public User(Long id, String name, String phone, String pwd, String uid,
            String heardPic, String age) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.pwd = pwd;
        this.uid = uid;
        this.heardPic = heardPic;
        this.age = age;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getHeardPic() {
        return this.heardPic;
    }
    public void setHeardPic(String heardPic) {
        this.heardPic = heardPic;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }


}
