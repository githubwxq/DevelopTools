package com.example.sdktestdemo;

import com.wxq.mvplibrary.base.BaseApp;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/13
 * desc:
 * version:1.0
 */
public class TestSdkApplication extends BaseApp{
    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
