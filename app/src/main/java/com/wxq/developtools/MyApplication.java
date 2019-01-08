package com.wxq.developtools;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.support.multidex.MultiDex;
import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.analytics.MobclickAgent;
import com.wxq.commonlibrary.util.ActivityUtils;
import com.wxq.commonlibrary.util.AppUtils;
import com.wxq.commonlibrary.util.BuglyUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.Utils;
import com.wxq.mvplibrary.base.BaseApp;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/6/23 0023.
 */

public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        BuglyUtils.init(this,"bd7d7fa0c2",BuildConfig.DEBUG);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    @Override
    public boolean setIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {
        final Intent intent2 = new Intent();
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setClass(this,MainActivity.class);
        startActivity(intent2);
        MobclickAgent.onKillProcess(getApplicationContext());
        ActivityUtils.finishAllActivitiesExceptNewest();
        Process.killProcess(Process.myPid());
    }
}
