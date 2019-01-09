package com.wxq.commonlibrary.InitTool;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.orhanobut.logger.Logger;


/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/1/11
 * @description
 */
public class InitService extends IntentService {
    public static String APP_KEY = "c5ac12a150334c1ab111e24701474170";


    public InitService() {
        super("InitService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
             //初始化耗时操作
            Logger.e("InitService","InitService");
//            Logger.init("exue")
//                    .methodCount(1)
//                    .hideThreadInfo()
//                    .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
//            EZOpenSDK.showSDKLog(BuildConfig.DEBUG);
//            //设置是否支持P2P取流,详见api
//            EZOpenSDK.enableP2P(false);
//            //APP_KEY请替换成自己申请的
//            EZOpenSDK.initLib(Global.application, APP_KEY, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
