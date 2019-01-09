package com.wxq.commonlibrary.base;

import android.app.Application;
import android.content.Context;

import java.util.List;


public interface IModuleConfig {
    void injectAppLifecycle(Context context, List<IAppLife> iAppLifes);

    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleCallbackses);

}
