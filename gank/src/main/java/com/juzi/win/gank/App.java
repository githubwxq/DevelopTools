package com.juzi.win.gank;

import com.wxq.commonlibrary.base.BaseApp;

/**
 * @author 文庆
 * @date 2019/1/8.
 * @description
 */

public class App extends BaseApp {
    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
