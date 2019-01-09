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

    @Generated(hash = 422536127)
    public User(String name) {
        this.name = name;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
