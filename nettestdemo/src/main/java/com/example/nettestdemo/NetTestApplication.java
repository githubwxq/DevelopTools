package com.example.nettestdemo;

import android.app.Application;
import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wxq.mvplibrary.base.IAppLife;
import com.wxq.mvplibrary.base.IModuleConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        wxq
 * 创建时间:    2017/9/16      16:12
 * QQ:             1981367757
 */

public class NetTestApplication implements IModuleConfig, IAppLife {
//    private static NewsComponent newsComponent;

    @Override
    public void injectAppLifecycle(Context context, List<IAppLife> iAppLifes) {
        iAppLifes.add(this);
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleCallbackses) {

    }

    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate(Application application) {

        initDB(application);
    }

    @Override
    public void onTerminate(Application application) {

    }

    private void initDB(Application application) {

    }


}
