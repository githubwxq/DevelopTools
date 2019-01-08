package com.juziwl.uilibrary.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import com.juziwl.uilibrary.R;

public class NotificationUtils {


    public static void showCommonNofition(Context context, String title, String content, Intent notificationIntent ) {
        String id="id";
        String name="name";
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if (channel == null) {
                // 传入参数：通道ID，通道名字，通道优先级（类似曾经的 builder.setPriority()）
                channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                // 开启指示灯，如果设备有的话
                channel.enableLights(true);
                // 设置指示灯颜色
                channel.setLightColor(Color.WHITE);
                // 是否在久按桌面图标时显示此渠道的通知
                channel.setShowBadge(true);
                // 设置是否应在锁定屏幕上显示此频道的通知
                channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PRIVATE);
                // 设置绕过免打扰模式
                channel.setBypassDnd(true);
                manager.createNotificationChannel(channel);
            }
            mBuilder = new NotificationCompat.Builder(context, id);
        } else {
            mBuilder = new NotificationCompat.Builder(context);
        }
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.common_layout_common_dialog);

        mBuilder.setContentTitle(title)
//                .setCustomBigContentView(new RemoteViews(context.getPackageName(),R.layout.common_layout_common_dialog))
                .setContentText(content)
                .setContentIntent(intent)
                .setAutoCancel(true)
//              .setNumber(++pushNum) //设置通知集合的数量
                //通知首次出现在通知栏，带上升动画效果的
//                .setProgress(100, 0, false)
                .setTicker("有一条新消息")
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_common_dial);
        Notification notify = mBuilder.build();

        notify.flags |= Notification.FLAG_NO_CLEAR;
//        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(1111, notify);
    }

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
                appName = activity.getString(activity.getResources().getIdentifier("string", "app_name", activity.getPackageName()));
            }
            String message = "检测到" + appName + "的通知权限没有打开，为了您能及时收到通知，" +
                    "请点击前往打开，然后点击通知或通知管理，打开允许通知的开关即可";
//            CommonDialog.getInstance().createDialog(activity, message, "前往打开",
//                    v ->openAppInfo(activity)).show();
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
