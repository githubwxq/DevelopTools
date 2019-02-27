package com.wxq.commonlibrary.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.wxq.commonlibrary.R;
import com.wxq.commonlibrary.constant.GlobalContent;

import java.io.File;
import java.util.Locale;
import okhttp3.OkHttpClient;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/11/22
 * @description 版本相关的工具类
 */
public class VersionUtils {

    private static final int DOWNLOAD_NOTIFICATION_ID = 666;
    private static boolean isDownload = false;

    public static final String DOWNLOAD_NOTIFICATION_CHANNAL_ID = "download_notification_channel_id";
    public static final String DOWNLOAD_NOTIFICATION_CHANNAL_NAME = "download_notification_channel_name";


    /**
     * 获取当前的版本号
     */
    public static String getVersionName() {
        PackageManager packageManager = Utils.getApp().getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(Utils.getApp().getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 比较两个版本号，版本号不能写成1.2.42这样的，要写成1.2.4.2
     *
     * @return 0 相等，1 version1 > version2，-1 version1 < version2
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        //获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        //循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            //如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }


    public static void download(String title, String url) {
        if (isDownload) {
            ToastUtils.showShort("正在下载中，您可以下拉通知栏查看下载进度");
            return;
        }
        Context context = Utils.getApp();
        NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotifyManager == null) {
            return;
        }
        NotificationUtils.setChannel(mNotifyManager, DOWNLOAD_NOTIFICATION_CHANNAL_ID,
                DOWNLOAD_NOTIFICATION_CHANNAL_NAME);
        NotificationCompat.Builder mBuilder;

//        Build.VERSION.SDK_INT是指当前设备的API Level。 Android 8.0（API等级26）及以上开发
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            mBuilder = new NotificationCompat.Builder(Utils.getApp(), DOWNLOAD_NOTIFICATION_CHANNAL_ID);
        } else {
            mBuilder = new NotificationCompat.Builder(Utils.getApp());
        }
        mBuilder.setContentTitle(title)
                .setContentText("已下载0%")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setProgress(100, 0, false)
                .setSmallIcon(getResourceId("ic_launcher", "mipmap"));
        Notification notify = mBuilder.build();
        notify.flags |= Notification.FLAG_NO_CLEAR;
        mNotifyManager.notify(DOWNLOAD_NOTIFICATION_ID, notify);

        File apk = new File(GlobalContent.filePath, GlobalContent.APKFILENAME);
        if (!apk.getParentFile().exists()) {
            apk.getParentFile().mkdirs();
        }
        if (apk.exists()) {
            apk.delete();
        }
        OkGo.<File>get(url)
                .client(new OkHttpClient.Builder().build())
                .execute(new FileCallback(apk.getParent(), apk.getName()) {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        ToastUtils.showShort(R.string.common_download_tips);
                        isDownload = true;
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        mNotifyManager.cancel(DOWNLOAD_NOTIFICATION_ID);
                        isDownload = false;
//                        AppManager.getInstance().killAllActivity();
//                        <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/> 注意
                          AppUtils.installApp(response.body());
//                        installApk(context, response.body());
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        int percent = (int) (progress.fraction * 100);
                        mBuilder.setProgress(100, percent, false)
                                .setContentText(String.format(Locale.getDefault(), "已下载%d%%", percent));
                        mNotifyManager.notify(DOWNLOAD_NOTIFICATION_ID, mBuilder.build());
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        isDownload = false;
                        mNotifyManager.cancel(DOWNLOAD_NOTIFICATION_ID);
                        ToastUtils.showShort("新版本下载失败，请检查网络");
                        try {
//                            AppManager.getInstance().killAllActivity();
//                            Intent intents = new Intent(context.getApplicationContext(), Class.forName(GlobalContent.loginActivity));
//                            if (intents.resolveActivity(context.getPackageManager()) != null) {
//                                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.getApplicationContext().startActivity(intents);
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        mNotifyManager.cancel(DOWNLOAD_NOTIFICATION_ID);
                        isDownload = false;
                    }
                });
    }
    /**
     * 根据名字获取资源的ID
     */
    public static int getResourceId(String resName, String resType) {
        Context context = Utils.getApp();
        return context.getResources().getIdentifier(resName, resType, context.getPackageName());
    }

    public static void installApk(Context context, File apkFile) {
        Intent intents = new Intent();
        intents.setAction("android.intent.action.VIEW");
        intents.addCategory("android.intent.category.DEFAULT");
        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intents.setDataAndType(getFileUri(context, apkFile), "application/vnd.android.package-archive");
        context.startActivity(intents);
    }

    public static Uri getFileUri(Context context, File file) {
        Uri uriForFile;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        } else {
            uriForFile = Uri.fromFile(file);
        }
        return uriForFile;
    }


}

// VersionUtils.download("qqqqqqq","https://dfsres-1254059237.cos.ap-shanghai.myqcloud.com/apppackage/app-release.apk");

