package com.example.im_huanxing;

import com.wxq.commonlibrary.base.BaseApp;

public class ImApplication extends BaseApp {
    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (getPackageName().equals(getCurrentProcessName(this))) {


        }
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
