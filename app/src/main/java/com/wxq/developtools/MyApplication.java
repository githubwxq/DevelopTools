package com.wxq.developtools;

import android.content.Intent;
import android.os.Process;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.PushAgent;
import com.wxq.commonlibrary.base.BaseApp;
import com.wxq.commonlibrary.util.ActivityUtils;
import com.wxq.commonlibrary.util.BuglyUtils;
import com.wxq.developtools.activity.KlookLoginActivity;

//import cn.jpush.android.api.JPushInterface;

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
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        initX5WebView();
//        SkinManager.init(this);
        //初始化百度地图
        SDKInitializer.initialize(this);



        initUmeng();


    }

    private void initUmeng() {

        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
// 参数一：当前上下文context；
// 参数二：应用申请的Appkey（需替换）；
// 参数三：渠道名称；
// 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
// 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5f4d9bae636b2b13182a1793", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "1582c3153e7b3fca5a82f030a773d04e");

        PushAgent mpu=PushAgent.getInstance(this);
    }

    @Override
    public boolean setIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {
//        final Intent intent2 = new Intent();
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent2.setClass(this, KlookLoginActivity.class);
//        startActivity(intent2);
//        MobclickAgent.onKillProcess(getApplicationContext());
//        ActivityUtils.finishAllActivitiesExceptNewest();
//        Process.killProcess(Process.myPid());
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
