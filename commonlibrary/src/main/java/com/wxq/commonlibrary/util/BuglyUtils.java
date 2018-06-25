package com.wxq.commonlibrary.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.StrictMode;

import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/3.
 */

public class BuglyUtils {

    public static void init(final Context context, String acount, boolean isDebug) {
        Bugly.setIsDevelopmentDevice(context, isDebug);
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(context);
        userStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            @Override
            public synchronized Map<String, String> onCrashHandleStart(int i, String s, String s1, String s2) {
                Map<String, String> map = new HashMap<>(5);
                return map;
            }
        });
        setStrictMode();
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = false;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFileUrl) {
                Logger.d("BetaPatchListener onPatchReceived = " + patchFileUrl);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
                Logger.d(String.format(Locale.getDefault(), "BetaPatchListener %s %d%%", Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
                Logger.d("BetaPatchListener onDownloadSuccess");
            }

            @Override
            public void onDownloadFailure(String msg) {
                Logger.d("BetaPatchListener onDownloadFailure msg = " + msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                Logger.d("BetaPatchListener onApplySuccess msg = " + msg);
            }

            @Override
            public void onApplyFailure(String msg) {
                Logger.d("BetaPatchListener onApplyFailure msg = " + msg);
            }

            @Override
            public void onPatchRollback() {
                Logger.d("BetaPatchListener onPatchRollback");
            }
        };
        Bugly.init(context, acount, isDebug, userStrategy);
    }

    @TargetApi(9)
    protected static void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }
}
