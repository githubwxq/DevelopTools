package com.example.bmobim;

import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseApp;
import com.wxq.commonlibrary.util.BuglyUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
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
 * desc: bmobim项目
 * version:1.0
 */
public class BmobImApplication extends BaseApp {

    private static BmobImApplication INSTANCE;

    public static BmobImApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(BmobImApplication app) {
        setBmobIMApplication(app);
    }

    private static void setBmobIMApplication(BmobImApplication a) {
        BmobImApplication.INSTANCE = a;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "0a3266bd5f48099941c32dea0467d6cf");
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
        //初始化im
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
        }



    }

    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }



    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
