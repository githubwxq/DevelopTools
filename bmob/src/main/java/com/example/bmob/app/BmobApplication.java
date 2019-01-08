package com.example.bmob.app;

import com.example.bmob.BuildConfig;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.wxq.commonlibrary.util.BuglyUtils;
import com.wxq.mvplibrary.base.BaseApp;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/04
 * desc: bmob项目
 * version:1.0
 */
public class BmobApplication extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "ea796f59c1a3d4a34f0b18b7626dd291");
        // 使用推送服务时的初始化操作
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {
                    Logger.i(bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Logger.e(e.getMessage());
                }
            }
        });
        // 启动推送服务
        BmobPush.startWork(this);




        //初始化bugly
        BuglyUtils.init(this,"bf24009ac5", BuildConfig.DEBUG);
        //初始化友盟社区化分享

    }

    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
