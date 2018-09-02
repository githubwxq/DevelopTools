package com.example.aroutertestdemo;

import com.wxq.mvplibrary.base.BaseApp;

/**
 * Created by 王晓清.
 * data 2018/9/2.
 * describe .
 */

public class ArouterApp extends BaseApp {
    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
