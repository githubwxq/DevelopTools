package com.wxq.commonlibrary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

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
    public String name;
    public String phone;
    public String pwd;
    public String uid;
    public String heardPic;
    public String age;
    @Generated(hash = 1906506140)
    public User(String name, String phone, String pwd, String uid, String heardPic,
            String age) {
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


}
