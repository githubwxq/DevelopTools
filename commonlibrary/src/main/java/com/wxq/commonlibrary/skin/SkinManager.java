//package com.wxq.commonlibrary.skin;
//
//import android.app.Activity;
//import android.app.Application;
//import android.os.Bundle;
//
//public class SkinManager {
//
//    public static SkinManager getInstance() {
//        return instance;
//    }
//
//    private static SkinManager instance;
//
//    private SkinManager(Application application) {
//        //注册activity生命周期
//        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                //
//
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//
//            }
//        });
//    }
//
//    public static void init(Application application){
//    synchronized (SkinManager.class){
//        if (null==instance) {
//            instance=new SkinManager(application);
//        }
//    }
//
//}
//
//
//}
