package com.example.uitestdemo;

import android.util.Log;

import com.wxq.commonlibrary.base.BaseApp;
import com.wxq.commonlibrary.util.AppUtils;
import com.wxq.commonlibrary.util.Utils;
/**
 * Created by 王晓清.
 * data 2018/9/2.
 * describe .
 */

public class UIApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);

        AppUtils.registerAppStatusChangedListener(this, new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                Log.e("wxq","onForeground");
            }

            @Override
            public void onBackground() {
                Log.e("wxq","onBackground");
            }
        });


    }

    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
