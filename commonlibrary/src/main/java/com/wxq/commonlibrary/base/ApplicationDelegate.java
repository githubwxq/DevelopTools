package com.wxq.commonlibrary.base;

import android.app.Application;
import android.content.Context;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by COOTEK on 2017/8/29.
 * 初始化的时候同时初始化各个module中的数据通过解析manifestparser
 */

public class ApplicationDelegate implements IAppLife {
    private List<IModuleConfig> list;
    private List<IAppLife> appLifes;
    private List<Application.ActivityLifecycleCallbacks> liferecycleCallbacks;


    public ApplicationDelegate() {
        appLifes = new ArrayList<>();
        liferecycleCallbacks = new ArrayList<>();
    }

    @Override
    public void attachBaseContext(Context base) {
        ManifestParser manifestParser = new ManifestParser(base);
        list = manifestParser.parse();
        if (list != null && list.size() > 0) {
            for (IModuleConfig configModule :
                    list) {
                configModule.injectAppLifecycle(base, appLifes);
                configModule.injectActivityLifecycle(base, liferecycleCallbacks);
            }
        }
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life :
                    appLifes) {
                life.attachBaseContext(base);
            }
        }
    }

    @Override
    public void onCreate(Application application) {
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life :
                    appLifes) {
                life.onCreate(application);
            }
        }
        if (liferecycleCallbacks != null && liferecycleCallbacks.size() > 0) {
            for (Application.ActivityLifecycleCallbacks life :
                    liferecycleCallbacks) {
                application.registerActivityLifecycleCallbacks(life);
            }
        }
    }

    @Override
    public void onTerminate(Application application) {
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life :
                    appLifes) {
                life.onTerminate(application);
            }
        }
        if (liferecycleCallbacks != null && liferecycleCallbacks.size() > 0) {
            for (Application.ActivityLifecycleCallbacks life :
                    liferecycleCallbacks) {
                application.unregisterActivityLifecycleCallbacks(life);
            }
        }
    }
}
