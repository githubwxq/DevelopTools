package com.wxq.developtools;

import android.content.Intent;
import android.os.Process;
import android.util.Log;

import com.tencent.bugly.beta.Beta;
import com.wxq.developtools.activity.KlookLoginActivity;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.wxq.commonlibrary.base.BaseApp;
import com.wxq.commonlibrary.util.ActivityUtils;
import com.wxq.commonlibrary.util.BuglyUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/6/23 0023.
 */
public class MyApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        //升级问题
        Beta.autoCheckUpgrade = true;
        Beta.checkUpgrade();
        BuglyUtils.init(this,"1400183086",BuildConfig.DEBUG);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initX5WebView();
    }

    @Override
    public boolean setIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {
        final Intent intent2 = new Intent();
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setClass(this, KlookLoginActivity.class);
        startActivity(intent2);
        MobclickAgent.onKillProcess(getApplicationContext());
        ActivityUtils.finishAllActivitiesExceptNewest();
        Process.killProcess(Process.myPid());
    }



    private void initX5WebView() {
        try {
            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                @Override
                public void onViewInitFinished(boolean arg0) {
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    Log.d("app", " onViewInitFinished is " + arg0);
                }

                @Override
                public void onCoreInitFinished() {
                    // TODO Auto-generated method stub
                }
            };
            //x5内核初始化接口
            QbSdk.initX5Environment(getApplicationContext(), cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
