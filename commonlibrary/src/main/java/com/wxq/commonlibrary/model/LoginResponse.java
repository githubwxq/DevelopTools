package com.wxq.commonlibrary.model;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/23
 * desc:测试登入数据
 * version:1.0
 */
public class LoginResponse {

    public String userId;
    public String phone;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId='" + userId + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
