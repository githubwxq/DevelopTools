package com.example.huanxing;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.wxq.commonlibrary.base.BaseApp;
import com.wxq.commonlibrary.model.AppConfig;
import com.wxq.commonlibrary.util.LogUtils;
import com.wxq.commonlibrary.util.StringUtils;

import org.android.agoo.xiaomi.MiPushRegistar;

public class HuanXingApplication  extends BaseApp {
    private final static String TAG = "HuanXingApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
// 参数一：当前上下文context；
// 参数二：应用申请的Appkey（需替换）；
// 参数三：渠道名称；
// 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
// 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5fd193f10537725bb62849a5", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "99dea70d2f2b237b67f013ab25cd3e36");
//获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("2222","注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("2222","注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });




        /*
         *该Handler是在IntentService中被调用，故 1.
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK 2.
         * IntentService里的onHandleIntent方法是并不处于主线程中，因此，如果需调用到主线程，需如下所示;
         * 或者可以直接启动Service
         * */
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtils.e("dealWithCustomMessage",
                                    "==============收到友盟推送===============");

                            UTrack.getInstance(context)
                                    .trackMsgClick(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                return super.getNotification(context, uMessage);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
//                super.dealWithCustomAction(context, msg);
                LogUtils.e(TAG, "dealWithCustomAction===》" + "收到友盟推送");

            }

            @Override
            public void launchApp(Context context, UMessage msg) {
                LogUtils.e(TAG, "launchApp===》" + "收到友盟推送");
                super.launchApp(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                LogUtils.e(TAG, "openUrl===》" + "收到友盟推送");
                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                LogUtils.e(TAG, "openActivity===》" + "收到友盟推送");
                super.openActivity(context, msg);
            }

            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                LogUtils.e(TAG, "handleMessage===》" + "收到友盟推送");
                super.handleMessage(context, uMessage);
            }
        };

        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setDisplayNotificationNumber(3);
//        Log.e(TAG, "mPushAgent register");







    }

    @Override
    public boolean setIsDebug() {
        return false;
    }

    @Override
    public void dealWithException(Thread thread, Throwable throwable, String error) {

    }
}
