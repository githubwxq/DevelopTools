package com.example.bmob.app;

import com.wxq.mvplibrary.base.BaseApp;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/04
 * desc:
 * version:1.0
 */
public class BmobApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
