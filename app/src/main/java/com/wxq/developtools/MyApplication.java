package com.wxq.developtools;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.wxq.commonlibrary.util.Utils;

/**
 * Created by Administrator on 2018/6/23 0023.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
       Utils.init(this);

    }
}
