package com.example.aroutertestdemo;

import android.app.Application;
import android.content.Context;

import com.wxq.mvplibrary.base.IAppLife;
import com.wxq.mvplibrary.base.IModuleConfig;

import java.util.List;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/12
 * desc: 主的application
 * version:1.0
 */
public class ArouterApplication implements IModuleConfig,IAppLife {


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
       //进行初始化


    }

    @Override
    public void onTerminate(Application application) {

    }
}
