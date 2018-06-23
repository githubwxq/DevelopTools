package com.juziwl.uilibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;

import com.wxq.commonlibrary.util.AppUtils;
import com.wxq.commonlibrary.util.ResourceUtils;


/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/5/10
 * @description 通知的工具类
 */
public class NotificationUtils {
    /**
     * 判断当前App的通知权限有没有打开
     */
    public static void isNotificationOpen(Activity activity) {
        boolean enabled = NotificationManagerCompat.from(activity).areNotificationsEnabled();
        if (!enabled) {
            String appName;
            try {
                PackageManager packageManager = activity.getPackageManager();
                appName = packageManager.getPackageInfo(activity.getPackageName(), 0).applicationInfo.loadLabel(packageManager).toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                appName = activity.getString(ResourceUtils.getResourceId("string", "app_name"));
            }
            String message = "检测到" + appName + "的通知权限没有打开，为了您能及时收到通知，" +
                    "请点击前往打开，然后点击通知或通知管理，打开允许通知的开关即可";
            CommonDialog.getInstance().createDialog(activity, message, "前往打开",
                    v ->openAppInfo(activity)).show();
        }
    }

    /**
     * 打开当前App的详情界面
     */
    public static void openAppInfo(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
