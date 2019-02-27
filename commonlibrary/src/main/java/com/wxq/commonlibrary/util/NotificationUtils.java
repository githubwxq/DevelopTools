package com.wxq.commonlibrary.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/9/10
 * @description
 */
public class NotificationUtils {

    /**
     * android 8.0以上设置notification的channel
     */
    public static void setChannel(NotificationManager mNotificationManager, String id, CharSequence name) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            NotificationChannel channel = mNotificationManager.getNotificationChannel(id);
            if (channel == null) {
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
                mNotificationManager.createNotificationChannel(channel);
            }
        }
    }
}
