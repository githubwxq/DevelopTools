package com.example.module_login;

import com.wxq.commonlibrary.base.BaseApp;

import cn.bmob.v3.Bmob;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/04
 * desc: bmob项目
 * version:1.0
 */
public class LoginModuleApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "ea796f59c1a3d4a34f0b18b7626dd291");

    }

    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
