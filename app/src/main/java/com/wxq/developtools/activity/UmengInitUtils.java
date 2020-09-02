//package com.wxq.developtools.activity;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.RemoteViews;
//
//import androidx.core.app.NotificationCompat;
//
//import com.alibaba.fastjson.JSON;
//import com.hyphenate.chat.EMMessage;
//import com.hyphenate.util.EasyUtils;
//import com.qianfan.forum.MainTabActivity;
//import com.qianfan.forum.MyApplication;
//import com.qianfan.forum.R;
//import com.qianfan.forum.activity.Chat.ServiceDetailActivity;
//import com.qianfan.forum.activity.Forum.HomeHotActivity;
//import com.qianfan.forum.activity.Forum.PostActivity;
//import com.qianfan.forum.activity.HomeSpecialTopicActivity;
//import com.qianfan.forum.activity.Pai.PaiDetailActivity;
//import com.qianfan.forum.activity.Pai.PaiFriendActivity;
//import com.qianfan.forum.activity.Pai.PaiTagActivity;
//import com.qianfan.forum.activity.StartActivity;
//import com.qianfan.forum.apiservice.OtherService;
//import com.qianfan.forum.base.retrofit.BaseEntity;
//import com.qianfan.forum.base.retrofit.QfCallback;
//import com.qianfan.forum.common.AppConfig;
//import com.qianfan.forum.easemob.model.EaseNotifier;
//import com.qianfan.forum.entity.live.LiveBulletEntity;
//import com.qianfan.forum.entity.live.LiveGiftEntity;
//import com.qianfan.forum.entity.live.LiveOtherEntity;
//import com.qianfan.forum.event.UmengDialogEvent;
//import com.qianfan.forum.event.chat.UMessageEvent;
//import com.qianfan.forum.service.DBService;
//import com.qianfan.forum.wedgit.dialog.UmengDialog;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;
//import com.umeng.message.IUmengCallback;
//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.MsgConstant;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UTrack;
//import com.umeng.message.UmengMessageHandler;
//import com.umeng.message.UmengNotificationClickHandler;
//import com.umeng.message.entity.UMessage;
//import com.umeng.socialize.PlatformConfig;
//import com.wangjing.dbhelper.utils.UserDataUtils;
//import com.wangjing.retrofitutils.RetrofitUtils;
//import com.wangjing.utilslibrary.ApplicationUtils;
//import com.wangjing.utilslibrary.LogUtils;
//import com.wangjing.utilslibrary.StringUtils;
//import com.wangjing.utilslibrary.sharedpreferences.SpUtils;
//import com.wangjing.utilslibrary.sharedpreferences.SpUtilsConfig;
//import com.wxq.commonlibrary.util.IntentUtils;
//
//import org.android.agoo.huawei.HuaWeiRegister;
//import org.android.agoo.mezu.MeizuRegister;
//import org.android.agoo.oppo.OppoRegister;
//import org.android.agoo.vivo.VivoRegister;
//import org.android.agoo.xiaomi.MiPushRegistar;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Map;
//
//import de.greenrobot.event.EventBus;
//import retrofit2.Call;
//
//public class UmengInitUtils {
//    private final static String TAG = InitUtils.class.getSimpleName();
//    private static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";
//
//    private static PushAgent mPushAgent;//友盟推送
//
//    private static boolean isshowUmengDialog = false;
//    private static boolean isLookUmengMsg = false;
//    public static int num = 0;//收到的推送消息数，用于角标展示
//
//    /**
//     * 初始化友盟推送
//     */
//    public static void initPush(final Context mContext) {
//        UMConfigure.init(mContext, mContext.getString(R.string.umeng_app_key), mContext.getString(R.string.umeng_channel), UMConfigure.DEVICE_TYPE_PHONE,
//                mContext.getString(R.string.umeng_message_secret));
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//        UMConfigure.setLogEnabled(Utils.getBooleanFromConfig(R.string.debug));
//
//        String wx_appId = mContext.getString(R.string.wx_appId);
//        String wx_appSecret = mContext.getString(R.string.wx_appSecret);
//        if (!TextUtils.isEmpty(wx_appId) && !TextUtils.isEmpty(wx_appSecret)) {
//            PlatformConfig.setWeixin(mContext.getString(R.string.wx_appId), mContext.getString(R.string.wx_appSecret));
//        } else {
//            LogUtils.e("微信appId or Secret 不能为空");
//        }
//        String sina_appId = mContext.getString(R.string.sina_appId);
//        String sina_appSecret = mContext.getString(R.string.sina_appSecret);
//        String sina_callbackUri = mContext.getString(R.string.sina_callbackUri);
//        if (!TextUtils.isEmpty(sina_appId) && !TextUtils.isEmpty(sina_appSecret) && !TextUtils.isEmpty(sina_callbackUri)) {
//            PlatformConfig.setSinaWeibo(mContext.getString(R.string.sina_appId), mContext.getString(R.string.sina_appSecret), mContext.getString(R.string.sina_callbackUri));
//        } else {
//            LogUtils.e("新浪微博appId or Secret or callbackUri不能为空");
//        }
//        String qq_appId = mContext.getString(R.string.qq_appId);
//        String qq_appSecret = mContext.getString(R.string.qq_appSecret);
//        if (!TextUtils.isEmpty(qq_appId) && !TextUtils.isEmpty(qq_appSecret)) {
//            PlatformConfig.setQQZone(mContext.getString(R.string.qq_appId), mContext.getString(R.string.qq_appSecret));
//        } else {
//            LogUtils.e("QQ appId or Secret 不能为空");
//        }
//
//
//        mPushAgent = getmPushAgent(mContext);
//        mPushAgent.setResourcePackageName(mContext.getResources().getString(R.string.package_name));
//        mPushAgent.setNotificaitonOnForeground(false);
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String s) {
//                Log.e(TAG, "umeng deviceToken:" + s);
//                MyApplication.setUmengDeviceToken(s);
//                uploadUmengDeviceToken(s, mContext);
//                if (TextUtils.isEmpty(MyApplication.getUmid())) {
//                    //当本地与友盟同步方法获取Umid都为空时，需重新请求并更新uuid
//                    //由于在getUuid->requestUuid->UMConfigure.getUMIDString已经重新获取了umid，所以此处不需要二次更新umid了
////                    CloudAdUtils.INSTANCE.getUuid(null);
//                }
//                Intent intent = new Intent(CALLBACK_RECEIVER_ACTION);
//                mContext.sendBroadcast(intent);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e(TAG, "failure umeng info s:" + s + "s1:" + s1);
//                Intent intent = new Intent(CALLBACK_RECEIVER_ACTION);
//                mContext.sendBroadcast(intent);
//            }
//        });
//        setUmengMessage(mContext);
//        MiPushRegistar.register(mContext, mContext.getString(R.string.xiaomi_app_id), mContext.getString(R.string.xiaomi_app_key));
//        if (Utils.isHuawei()) {
//            HuaWeiRegister.register(ApplicationUtils.getApp());
//        }
//        MeizuRegister.register(mContext, mContext.getString(R.string.meizu_app_id), mContext.getString(R.string.meizu_app_key));
//        if (!StringUtils.isEmpty(mContext.getString(R.string.oppo_app_key))) {
//            OppoRegister.register(mContext, mContext.getString(R.string.oppo_app_key), mContext.getString(R.string.oppo_app_secret));
//        }
//        VivoRegister.register(mContext);
//    }
//
//    //上传Umeng获取的deviceToken到自己服务器
//    private static void uploadUmengDeviceToken(String deviceToken, Context mContext) {
//        Call<BaseEntity<Void>> call = RetrofitUtils.getInstance().creatBaseApi(OtherService.class).uploadUmengDeviceToken(deviceToken, MyApplication.getUmid());
//        call.enqueue(new QfCallback<BaseEntity<Void>>() {
//            @Override
//            public void onSuc(BaseEntity<Void> response) {
//
//            }
//
//            @Override
//            public void onOtherRet(BaseEntity<Void> response, int ret) {
//
//            }
//
//            @Override
//            public void onFail(Call<BaseEntity<Void>> call, Throwable t, int httpCode) {
//
//            }
//
//            @Override
//            public void onAfter() {
//
//            }
//        });
////        otherApi.uploadUmengDeviceToken(deviceToken,new QfResultCallback<String>(){
////            @Override
////            public void onBefore(Request request) {
////                super.onBefore(request);
////            }
////            @Override
////            public void onAfter() {
////                super.onAfter();
////            }
////            @Override
////            public void onSuccess(String response) {
////                super.onSuccess(response);
////            }
////            @Override
////            public void onError(Request request, Exception e, int errorCode) {
////                super.onError(request, e, errorCode);
////            }
////        });
//    }
//
//    public static PushAgent getmPushAgent(Context mContext) {
//        if (mPushAgent != null) {
//            return mPushAgent;
//        } else {
//            mPushAgent = PushAgent.getInstance(mContext);
//            return mPushAgent;
//        }
//    }
//
//
//    //设置友盟推送消息处理
//    private static void setUmengMessage(final Context mContext) {
//        /*
//         *该Handler是在IntentService中被调用，故 1.
//         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK 2.
//         * IntentService里的onHandleIntent方法是并不处于主线程中，因此，如果需调用到主线程，需如下所示;
//         * 或者可以直接启动Service
//         * */
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            @Override
//            public void dealWithCustomMessage(final Context context, final UMessage msg) {
//                new Handler(context.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            LogUtils.e("dealWithCustomMessage",
//                                    "==============收到友盟推送===============");
//                            insertUMengInfo(msg);
//                            UTrack.getInstance(context)
//                                    .trackMsgClick(msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public Notification getNotification(final Context context, final UMessage msg) {
//                LogUtils.e("getNotification",
//                        "==============收到友盟推送===============");
//                insertUMengInfo(msg);
//
//                //处理直播的数据
//                String type=handleLiveMessage(msg);
//
//                //收到推送展示华为角标
//                if (Integer.valueOf(type)>0){
//                    num = num + 1;
//                    UmengInitUtils.setHuaweiBadgeNum(mContext, num + EaseNotifier.getUnreadMsgCountTotal());
//
//                    //当友盟dialog弹出时，第二条友盟推送消息在通知栏显示，不弹框
//                    if (EasyUtils.isAppRunningForeground(context)) {
//                        LogUtils.e("getNotification", "isshowUmengDialog==>" + isshowUmengDialog);
//                        if (!isshowUmengDialog) {
//                            if (ApplicationUtils.getTopActivity() != null) {
//                                if (ApplicationUtils.getActivitySizeExcept(StartActivity.class) > 0 && !(ApplicationUtils.getTopActivity() instanceof StartActivity)) {
//                                    isshowUmengDialog = true;
//                                    mPushAgent.setNotificaitonOnForeground(false);
//                                    ApplicationUtils.getTopActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            new Handler(context.getMainLooper()).postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    showUmeng(context, msg);
//                                                }
//                                            }, 3000);
//                                        }
//                                    });
//                                    return null;
//                                }
//                            }
//                        }
//                    }
//                    mPushAgent.setNotificaitonOnForeground(true);
//                    //适配安卓8.0的消息渠道
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                        if (notificationManager != null) {
//                            NotificationChannel mChannel = notificationManager.getNotificationChannel(AppConfig.NOTIFICATION_CHANNEL_ID);
//                            if (mChannel == null) {
//                                mChannel = new NotificationChannel(AppConfig.NOTIFICATION_CHANNEL_ID, AppConfig.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//                                mChannel.setDescription(AppConfig.NOTIFICATION_CHANNEL_DESCRIPTION);
//                                // 设置通知出现时的闪灯（如果 android 设备支持的话）
//                                mChannel.enableLights(true);
//                                mChannel.setLightColor(Color.RED);
//                                // 设置通知出现时的震动（如果 android 设备支持的话）
//                                mChannel.enableVibration(true);
//                                notificationManager.createNotificationChannel(mChannel);
//                            }
//                        }
//                    }
//                    switch (msg.builder_id) {
//                        case 1:
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                            RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//                            myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                            myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                            myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                            myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
//                            builder.setContent(myNotificationView)
//                                    .setContentTitle(msg.title)
//                                    .setSmallIcon(getSmallIconId(context, msg))
//                                    .setContentText(msg.text)
//                                    .setTicker(msg.ticker)
//                                    .setAutoCancel(true);
//                            // create and send notificaiton
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                builder.setChannelId(AppConfig.NOTIFICATION_CHANNEL_ID);
//                            }
//                            Notification mNotification = builder.build();
//                            //由于Android v4包的bug，在2.3及以下系统，Builder创建出来的Notification，并没有设置RemoteView，故需要添加此代码
//                            mNotification.contentView = myNotificationView;
//                            return mNotification;
//                        default:
//                            Bitmap bitmap;
//                            if (StringUtils.isEmpty(msg.img)) {
//                                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//                            } else {
//                                bitmap = getLargeIcon(context, msg);
//                            }
//                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                                    .setLargeIcon(bitmap)
//                                    .setSmallIcon(R.mipmap.ic_notification)//ic_notification
//                                    .setContentTitle(msg.title)
//                                    .setContentText(msg.text)
//                                    .setTicker(msg.ticker)
//                                    .setAutoCancel(true);
//                            // create and send notificaiton
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                mBuilder.setChannelId(AppConfig.NOTIFICATION_CHANNEL_ID);
//                            }
//                            Notification notification = mBuilder
//                                    .build();
//                            return notification;
//                    }
//                }else {
//                    mPushAgent.setNotificaitonOnForeground(false);
//                    return null;
//                }
//            }
//        };
//        mPushAgent.setMessageHandler(messageHandler);
//
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
////                super.dealWithCustomAction(context, msg);
//                LogUtils.e(TAG, "dealWithCustomAction===》" + "收到友盟推送");
//                insertUMengInfo(msg);
//                dealWithUmengMessage(context, msg, true);
//            }
//
//            @Override
//            public void launchApp(Context context, UMessage msg) {
//                LogUtils.e(TAG, "launchApp===》" + "收到友盟推送");
//                super.launchApp(context, msg);
//            }
//
//            @Override
//            public void openUrl(Context context, UMessage msg) {
//                LogUtils.e(TAG, "openUrl===》" + "收到友盟推送");
//                super.openUrl(context, msg);
//            }
//
//            @Override
//            public void openActivity(Context context, UMessage msg) {
//                LogUtils.e(TAG, "openActivity===》" + "收到友盟推送");
//                super.openActivity(context, msg);
//            }
//
//            @Override
//            public void handleMessage(Context context, UMessage uMessage) {
//                LogUtils.e(TAG, "handleMessage===》" + "收到友盟推送");
//                super.handleMessage(context, uMessage);
//            }
//        };
//
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
//        mPushAgent.setDisplayNotificationNumber(3);
//        Log.e(TAG, "mPushAgent register");
//
//
////        mPushAgent.setUnregisterCallback(mUnregisterCallback);
//    }
//
//    private static String handleLiveMessage(UMessage msg){
//        String type = null;
//        for (Map.Entry<String, String> entry : msg.extra.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if (key.equals("type") || key.equals("TYPE")) {
//                type = value;
//                break;
//            }
//        }
//        try {
//            JSONObject jo=new JSONObject(msg.extra.toString());
//            String data=jo.getJSONObject("data").toString();
//
//            if (type.equals("-1")){
//                //直播弹幕
//                LiveBulletEntity bulletEntity= JSON.parseObject(data, LiveBulletEntity.class);
//
//                MyApplication.getBus().post(bulletEntity);
//            }else if (type.equals("-2")){
//                //直播礼物
//                LiveGiftEntity giftData= JSON.parseObject(data, LiveGiftEntity.class);
//
//                MyApplication.getBus().post(giftData);
//            }else if (type.equals("-3")){
//                //直播数据更新
//                LiveOtherEntity otherEntity= JSON.parseObject(data, LiveOtherEntity.class);
//                MyApplication.getBus().post(otherEntity);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return type;
//    }
//
//    /**
//     * 弹出友盟dialog
//     *
//     * @param context Context
//     * @param msg     UMessage
//     */
//    private static void showUmeng(final Context context, final UMessage msg) {
//        LogUtils.e("showUmeng", "执行了showUmeng  --" + ApplicationUtils.getTopActivity());
//        if (ApplicationUtils.getTopActivity() == null || ApplicationUtils.getTopActivity() instanceof StartActivity) {
//            isshowUmengDialog = false;
//            EventBus.getDefault().post(new UmengDialogEvent(msg));
//            return;
//        }
//        final UmengDialog umengDialog = new UmengDialog(ApplicationUtils.getTopActivity());
//        if (ApplicationUtils.getTopActivity() != null) {
//            umengDialog.setTitleAndContent(msg.title, msg.text);
//            umengDialog.setOnClickButtonListener("查看", "取消", new UmengDialog.OnClickButtonListener() {
//                @Override
//                public void OnClickSure(View v) {
//                    try {
//                        isshowUmengDialog = false;
//                        isLookUmengMsg = true;
//                        //自定义消息的点击统计
//                        UTrack.getInstance(context).trackMsgClick(msg);
//                        dealWithUmengMessage(context, msg, false);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void OnClickCancel(View v) {
//                    //自定义消息的忽略统计
//                    UTrack.getInstance(context).trackMsgDismissed(msg);
//                }
//            });
//        } else {
//            isshowUmengDialog = false;
//        }
//
//        umengDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                LogUtils.e("onDismiss", "执行了onDismiss");
//                isshowUmengDialog = false;
//                if (!isLookUmengMsg) {
//                    UTrack.getInstance(context).trackMsgDismissed(msg);
//                }
//                isLookUmengMsg = false;
//            }
//        });
//        if (needToShowUmengDialog(msg)) {
//            LogUtils.e("needToShowUmengDialog", " umengDialog.show()");
//            umengDialog.show();
//        }
//    }
//
//
//    /**
//     * 是否需要弹出友盟dialog
//     *
//     * @param msg UMessage
//     * @return boolean
//     */
//    private static boolean needToShowUmengDialog(UMessage msg) {
//        if (msg.title.equals("") && msg.text.equals("")) {//标题和者内容都为空时，不显示
//            isshowUmengDialog = false;
//            return false;
//        }
//        //已登陆时如果是服务号推送来的消息，则不弹窗
//        if (isServiceInfo(msg) && UserDataUtils.getInstance().isLogin()) {
//            return false;
//        }
//        if (!isShowNotify(msg)) {
//            isshowUmengDialog = false;
//            return false;
//        }
//
//        if (ApplicationUtils.getTopActivity() instanceof StartActivity) {
//            isshowUmengDialog = false;
//            return false;
//        }
//        isshowUmengDialog = true;
//        return true;
//    }
//
//
//    private static boolean isShowNotify(UMessage msg) {
//        String type = null;
//        String id = null;
//        String show_alert = null;//当该值为1时，显示弹窗
//        for (Map.Entry<String, String> entry : msg.extra.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if (key.equals("show_alert")) {
//                show_alert = value;
//            } else if (key.equals("type") || key.equals("TYPE")) {
//                type = value;
//            } else if (key.equals("id") || key.equals("ID")) {
//                id = value;
//            }
//        }
//        try {
//            if (show_alert != null && "1".equals(show_alert)) {
//                return true;
//            } else {
//                return false;
////                if (Integer.parseInt(type) < 0) {
////                    //直播相关
////                    return false;
////                }
////                if (type.equals("50") && id.contains("chat/?uid")) {
////                    //环信消息
////                    return false;
////                }
////                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 判断友盟消息是否需要插入本地数据库
//     *
//     * @param msg
//     */
//    private static boolean isNeedStorage(UMessage msg) {
//        //已登录状态不需要插入
//        if (UserDataUtils.getInstance().isLogin()) {
//            return false;
//        }
//        //如果是服务号推送来的消息，则需要插入
//        if (isServiceInfo(msg)) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断本消息是否为服务号推送的消息
//     *
//     * @param msg
//     * @return
//     */
//    private static boolean isServiceInfo(UMessage msg) {
//        if (msg.extra == null || msg.extra.size() == 0) {
//            return false;
//        }
//        //如果extra中的发送方ID：uid不为空，则本条消息需要保存在数据库中
//        for (Map.Entry<String, String> entry : msg.extra.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if (key.equals("uid")) {
//                if (!TextUtils.isEmpty(value)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 友盟消息插入本地数据库
//     *
//     * @param msg
//     */
//    private static void insertUMengInfo(UMessage msg) {
//        if (isNeedStorage(msg)) {
//            DBService.saveUMessage(msg);
//            MyApplication.getBus().post(new UMessageEvent());
////            MyApplication.getBus().postSticky(new UMessageEvent());
//        }
//    }
//
//    public static void dealWithUmengMessage(Context context, UMessage msg, boolean isGoToMain) {
//        Log.e(TAG, "dealWithUmengMessage" + "msg:" + msg.extra.toString());
//        String type = "";
//        String id = "";//帖子ID
//        String tagname = "";//话题的名字
//        String uid = "";//服务号用户ID
//        String service = "";//服务号ID
//        for (Map.Entry<String, String> entry : msg.extra.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if (key.equals("type") || key.equals("TYPE")) {
//                type = value;
//                Log.e("type", type);
//            } else if (key.equals("id") || key.equals("ID")) {
//                id = value;
//            } else if (key.equals("tagname") || key.equals("TAGNAME")) {
//                tagname = value;
//            } else if (key.equals("uid")) {
//                uid = value;
//            } else if (key.equals("service_Id")) {
//                service = value;
//            }
//        }
//        Intent intent = null;
//        boolean isNeedGoToMain = false;
//        if (type.equals("10")) {// 跳转帖子详情
//            isNeedGoToMain = true;
//            intent = new Intent(context, PostActivity.class);
//            intent.putExtra("tid", "" + id);
//        } else if (type.equals("20")) {// 跳转首页
//            isNeedGoToMain = false;
//            intent = new Intent(context, StartActivity.class);
//        } else if (type.equals("30")) {//跳转巷友圈详情
//            isNeedGoToMain = true;
//            Bundle bundle = new Bundle();
//            bundle.putString("id", "" + id);
//            intent = new Intent(context, PaiDetailActivity.class);
//            intent.putExtras(bundle);
//        } else if (type.equals("40")) {//跳转话题页
//            isNeedGoToMain = true;
//            Bundle bundle = new Bundle();
//            bundle.putString("tag_id", "" + id);
//            bundle.putString("tag_name", "" + tagname);
//            intent = new Intent(context, PaiTagActivity.class);
//            intent.putExtras(bundle);
//        } else if (type.equals("50")) {//查看更多跳转链接
//            isNeedGoToMain = true;
//            intent = Utils.getIntent(context, "" + id, null);
//        }
////        else if (type.equals("60")) {//跳转巷友圈消息列表
////            isNeedGoToMain = true;
////            Bundle bundle = new Bundle();
////            bundle.putInt("type", 1);
////            intent = new Intent(context, PaiMessageActivity.class);
////            intent.putExtras(bundle);
////        }
//        else if (type.equals("70")) {//跳转聊天列表
//            isNeedGoToMain = false;
//            intent = new Intent(context, MainTabActivity.class);
//            intent.putExtra("should_chat", true);
//        } else if (type.equals("80")) {//跳转交友推荐页
//            isNeedGoToMain = true;
//            intent = new Intent(context, PaiFriendActivity.class);
//        } else if (type.equals("90")) {//跳转专题
//            isNeedGoToMain = true;
//            intent = new Intent(context, HomeSpecialTopicActivity.class);
//            Bundle args = new Bundle();
//            args.putInt("sid", Integer.parseInt(id));
//            intent.putExtras(args);
//        } else if (type.equals("110")) {//服务号推送纯文字、图片,直接跳转到服务号详情
//            //区分是否是今日热门发来的
//            if (uid.equals("qianfan_daily_topic")) {
//                intent = new Intent(context, HomeHotActivity.class);
//            } else {
//                intent = new Intent(context, ServiceDetailActivity.class);
//                intent.putExtra(StaticUtil.ServiceDetail.SERVICE_ID, Integer.parseInt(service));
//            }
//        } else {
//            intent = new Intent(context, MainTabActivity.class);
//        }
//        if (isGoToMain && isNeedGoToMain) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            IntentUtils.startActivityAndGoToMain(context, intent);
//        } else {
//            if (intent == null) {
//                intent = new Intent(context, MainTabActivity.class);
//            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
//        UTrack.getInstance(context)
//                .trackMsgClick(msg);
//    }
//
//
//    /**
//     * 设置推送是否有声音
//     *
//     * @param enable
//     */
//    public static void enableNotificationSound(boolean enable) {
//        if (enable) {
//            getmPushAgent(ApplicationUtils.getApp()).setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//        } else {
//            getmPushAgent(ApplicationUtils.getApp()).setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//        }
//        SpUtils.getInstance().putBoolean(SpUtilsConfig.umeng_sound, enable);
//    }
//
//    /**
//     * 获取是否开启推送声音，用于设置按钮状态
//     *
//     * @return
//     */
//    public static boolean isNotificaitonSoundEnable() {
//        if (getmPushAgent(ApplicationUtils.getApp()).getNotificationPlaySound() == MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    /**
//     * 设置推送是否震动
//     *
//     * @param enable
//     */
//    public static void enableNotificationVibrate(boolean enable) {
//        if (mPushAgent == null) {
//            mPushAgent = getmPushAgent(ApplicationUtils.getApp());
//        }
//        if (enable) {
//            mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//        } else {
//            mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
//        }
//        SpUtils.getInstance().putBoolean(SpUtilsConfig.umeng_sock, false);
//    }
//
//    /**
//     * 设置是否开启推送震动，用于设置按钮状态
//     *
//     * @return
//     */
//    public static boolean isNotificaitonVibrateEnable() {
//        if (mPushAgent == null) {
//            mPushAgent = getmPushAgent(ApplicationUtils.getApp());
//        }
//        if (mPushAgent.getNotificationPlayVibrate() == MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static void enableNotification(boolean enable) {
//        if (mPushAgent == null) {
//            mPushAgent = getmPushAgent(ApplicationUtils.getApp());
//        }
//        if (enable) {
//            mPushAgent.enable(new IUmengCallback() {
//                @Override
//                public void onSuccess() {
//                    //开启成功
//                    Log.e(TAG, "友盟开启成功");
//                    MyApplication.pushStatus=true;
//                }
//
//                @Override
//                public void onFailure(String s, String s1) {
//                    MyApplication.pushStatus=false;
//                }
//            });
//        } else {
//            mPushAgent.disable(new IUmengCallback() {
//                @Override
//                public void onSuccess() {
//                    Log.e(TAG, "友盟关闭成功");
//                    MyApplication.pushStatus=false;
//                }
//
//                @Override
//                public void onFailure(String s, String s1) {
//
//                }
//            });
//        }
//    }
//
//    //显示华为角标
//    public static boolean setHuaweiBadgeNum(Context mContext, int num) {
//        try {
//            String launchClassName = getLauncherClassName(mContext);
//            if (TextUtils.isEmpty(launchClassName)) {
//                return false;
//            }
//            Bundle bundle = new Bundle();
//            bundle.putString("package", mContext.getPackageName());//包名
//            bundle.putString("class", launchClassName);//桌面图标对应的应用入口Activity类
//            bundle.putInt("badgenumber", num);//数量
//            mContext.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher" + ".settings/badge/"), "change_badge", null, bundle);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    //环信消息也显示华为角标
//    public static void setChatHuaweiBadgeNum(EMMessage emMessage) {
//        try {
//            // 设置自定义推送提示
//            JSONObject extObject = new JSONObject();
//            extObject.put("em_huawei_push_badge_class", getLauncherClassName(MyApplication.mContext));
//            // 将推送扩展设置到消息中
//            emMessage.setAttribute(StaticUtil.ChatActivity.TXT_EM_APNS_EXT, extObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String getLauncherClassName(Context context) {
//        ComponentName launchComponent = getLauncherComponentName(context);
//        if (launchComponent == null) {
//            return "";
//        } else {
//            return launchComponent.getClassName();
//        }
//    }
//
//    private static ComponentName getLauncherComponentName(Context context) {
//        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context
//                .getPackageName());
//        if (launchIntent != null) {
//            return launchIntent.getComponent();
//        } else {
//            return null;
//        }
//    }
//
//
//}
