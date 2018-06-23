package com.wxq.developtools;

import android.app.Application;

import com.wxq.commonlibrary.util.Utils;

/**
 * Created by Administrator on 2018/6/23 0023.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       Utils.init(this);
    }
}
