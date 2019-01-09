package com.wxq.commonlibrary.base;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.beta.Beta;
import com.wxq.commonlibrary.util.AppManager;
import com.wxq.commonlibrary.util.FileLogAdapter;
import com.wxq.commonlibrary.util.Utils;

/**基类app对象
 * @author wxq
 */
public abstract class BaseApp extends Application implements Thread.UncaughtExceptionHandler {

    private static BaseApp mContext;
    public static boolean isDebug = false;
    private ApplicationDelegate applicationDelegate;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        applicationDelegate = new ApplicationDelegate();
        applicationDelegate.attachBaseContext(base);
        MultiDex.install(this);
//         安装tinker
        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        isDebug = setIsDebug();
        //工具初始化
        Utils.init(this);
        initLog();
        Stetho.initializeWithDefaults(this);
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 打印日志
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        //通过反射调用其他application
        applicationDelegate.onCreate(this);
    }

    //初始化简单的app全局的dragger
    private void initDagger() {


    }

//    private void initX5WebView() {
//        try {
//            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//                @Override
//                public void onViewInitFinished(boolean arg0) {
//                    // TODO Auto-generated method stub
//                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                    Log.d("app", " onViewInitFinished is " + arg0);
//                }
//
//                @Override
//                public void onCoreInitFinished() {
//                    // TODO Auto-generated method stub
//                }
//            };
//            //x5内核初始化接口
//            QbSdk.initX5Environment(getApplicationContext(), cb);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static BaseApp getContext() {
        return mContext;
    }

    public abstract boolean setIsDebug();


    public void initLog() {
        Logger.init("wxq").logLevel(LogLevel.FULL).logAdapter(new FileLogAdapter()).hideThreadInfo().methodCount(0);
        Logger.init("wxq").logLevel(LogLevel.FULL);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        dealWithException(thread, throwable, throwable.getMessage());
    }

    public abstract void dealWithException(Thread thread, Throwable throwable, String error);


    @Override
    public void onTerminate() {
        super.onTerminate();
        applicationDelegate.onTerminate(this);
        exitApp();
    }


    /**
     * 退出应用
     */
    public void exitApp() {
        AppManager.getInstance().killAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }
    // 程序在内存清理的时候执行
    /**
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }


}