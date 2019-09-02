package com.wxq.commonlibrary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * shu
 */
@Entity
public class AppConfig {

    public String getLoginStatue() {
        return loginStatue;
    }

    public void setLoginStatue(String loginStatue) {
        this.loginStatue = loginStatue;
    }

    public String loginStatue;  //"0" 自动登录  "1" 非自动

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String token;  //"0" 自动登录  "1" 非自动

    @Generated(hash = 1664613887)
    public AppConfig(String loginStatue, String token) {
        this.loginStatue = loginStatue;
        this.token = token;
    }

    @Generated(hash = 136961441)
    public AppConfig() {
    }

}
