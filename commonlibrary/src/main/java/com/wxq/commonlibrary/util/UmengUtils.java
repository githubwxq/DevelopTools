//package com.wxq.commonlibrary.util;
//
//import android.app.Activity;
//import android.content.Context;
//import com.umeng.analytics.MobclickAgent;
//import com.wxq.commonlibrary.channel.DNChannelHelper;
//
///**
// * @author 王晓清
// * @version V_1.0.0
// * @date 2017/10/9
// * @description 友盟统计分享第三方登入sdk简单封装
// */
//
//public class UmengUtils {
//
//    public static void init(Context context, String appkey, boolean isTeacher) {
//        String channel = "orange";
//        try {
//            channel = DNChannelHelper.getChannel(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(context, appkey, channel);
//        MobclickAgent.startWithConfigure(config);
//        //DNChannelHelper.getChannel(this)
//        //开启友盟统计
//        MobclickAgent.setSessionContinueMillis(1000 * 60);
//        MobclickAgent.openActivityDurationTrack(false);
//        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        MobclickAgent.setDebugMode(true);
//
//        //设置分享和第三方登入
//        UMConfigure.setLogEnabled(true);
//        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
//        UMConfigure.init(context, appkey, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
//        //开启ShareSDK debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        Config.DEBUG = true;
//        if (isTeacher) {
//            //教学du配置
//            if (!Global.TeaUrlApi.contains("jzexueyun.com")) {
//                PlatformConfig.setWeixin(Constants.TEA_WEIXIN_APPID_TEST, Constants.TEA_WEIXIN_SECRET_TEST);
//                PlatformConfig.setQQZone(Constants.TEA_QQ_APPID_TEST, Constants.TEA_QQ_APPKEY_TEST);
//            } else {
//                PlatformConfig.setWeixin(Constants.TEA_WEIXIN_APPID, Constants.TEA_WEIXIN_SECRET);
//                PlatformConfig.setQQZone(Constants.TEA_QQ_APPID, Constants.TEA_QQ_APPKEY);
//            }
////            PlatformConfig.setSinaWeibo(Constants.WEIBO_APPKEY, Constants.WEIBO_SECRET,"http://www.weibo.com");
//        } else {
//            //家长端配置
//            if (!Global.ParUrlApi.contains("jzexueyun.com")) {
//                PlatformConfig.setWeixin(Constants.PAR_WEIXIN_APPID_TEST, Constants.PAR_WEIXIN_SECRET_TEST);
//                PlatformConfig.setQQZone(Constants.PAR_QQ_APPID_TEST, Constants.PAR_QQ_APPKEY_TEST);
//            } else {
//                PlatformConfig.setWeixin(Constants.PAR_WEIXIN_APPID, Constants.PAR_WEIXIN_SECRET);
//                PlatformConfig.setQQZone(Constants.PAR_QQ_APPID, Constants.PAR_QQ_APPKEY);
//            }
//            PlatformConfig.setSinaWeibo(Constants.WEIBO_APPKEY, Constants.WEIBO_SECRET, "http://www.weibo.com");
//        }
//        try {
//            UMShareConfig shareConfig = new UMShareConfig();
//            shareConfig.isNeedAuthOnGetUserInfo(true);
//            UMShareAPI.get(context).setShareConfig(shareConfig);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 如果页面是直接由Activity实现的，统计代码大约是这样：
//     */
//    public static void onResume(Activity activity, String tag) {
//        MobclickAgent.onPageStart(tag);
//        MobclickAgent.onResume(activity);
//    }
//
//    public static void onPause(Activity activity, String tag) {
//        MobclickAgent.onPageEnd(tag);
//        MobclickAgent.onPause(activity);
//    }
//
//    public static boolean isQQINstall(Activity activity) {
//        if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
//            ToastUtils.showToast("请先安装QQ客户端");
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean isWechatINstall(Activity activity) {
//        if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
//            ToastUtils.showToast("请先安装微信客户端");
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean isSinINstall(Activity activity) {
//        if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.SINA)) {
//            ToastUtils.showToast("请先安装微博客户端");
//            return false;
//        }
//        return true;
//    }
//
//
//    /**
//     * 基本分享的操作
//     *
//     * @param url    要分享的链接
//     * @param title  分享出去要显示的标题
//     * @param picurl 分享出去显示的图片链接，如果传空默认取app的图标
//     * @param desc   分享出去要显示的详情
//     */
//    private static UMWeb baseShareSetting(Activity activity, String title, String picurl, String desc, String url) {
//        UMImage image;
//        if (StringUtils.isEmpty(picurl)) {
//            image = new UMImage(activity, CommonTools.getResourceId("main_icon", "mipmap"));
//        } else {
//            //是不是都是数字 是的话转资源id
//            if (android.text.TextUtils.isDigitsOnly(picurl)) {
//                image = new UMImage(activity, Integer.parseInt(picurl));
//            } else {
//                image = new UMImage(activity, picurl);
//            }
//        }
//        UMWeb web = new UMWeb(url);
//        //标题
//        web.setTitle(title);
//        web.setThumb(image);
//        //描述
//        web.setDescription(desc);
//        return web;
//    }
//
//    /**
//     * 基本视频分享的操作
//     *
//     * @param videoUrl   要分享的链接
//     * @param videoTitle 分享出去要显示的标题
//     * @param picUrl     分享出去显示的图片链接，如果传空默认取app的图标
//     * @param desc       分享出去要显示的详情
//     */
//    private static UMVideo baseVideoSetting(Activity activity, String videoTitle, String picUrl, String desc, String videoUrl) {
//        UMImage image;
//        if (StringUtils.isEmpty(picUrl)) {
//            image = new UMImage(activity, CommonTools.getResourceId("main_icon", "mipmap"));
//        } else {
//            //是不是都是数字 是的话转资源id
//            if (android.text.TextUtils.isDigitsOnly(picUrl)) {
//                image = new UMImage(activity, Integer.parseInt(picUrl));
//            } else {
//                image = new UMImage(activity, picUrl);
//            }
//        }
//
//
//        UMVideo web = new UMVideo(videoUrl);
//        //标题
//        web.setTitle(videoTitle);
//        web.setThumb(image);
//        //描述
//        web.setDescription(desc);
//        return web;
//    }
//
//
//    //分享链接
//    public static final String COMMONSHARE = "commonShare";
//    //分享视频
//    public static final String VIDEOSHARE = "videoshare";
//
//    // 分享QQ
//    public static void shareQQ(Activity activity, String title, String picurl, String desc, String url, String type) {
//        if (StringUtils.isEmpty(url)) {
//            ToastUtils.showToast(R.string.sdk_invalid_url);
//            return;
//        }
//        if (isQQINstall(activity)) {
//            if (type.equals(COMMONSHARE)) {
//                new ShareAction(activity).withMedia(baseShareSetting(activity, title, picurl, desc, url)).setPlatform(SHARE_MEDIA.QQ).setCallback(shareListener).share();
//            }
//            if (type.equals(VIDEOSHARE)) {
//                new ShareAction(activity).withMedia(baseVideoSetting(activity, title, picurl, desc, url)).setPlatform(SHARE_MEDIA.QQ).setCallback(shareListener).share();
//            }
//        } else {
//            ToastUtils.showToast("请先安装QQ客户端");
//        }
//    }
//
//    // 分享微信
//    public static void shareWX(Activity activity, String title, String picurl, String desc, String url, String type) {
//        if (StringUtils.isEmpty(url)) {
//            ToastUtils.showToast(R.string.sdk_invalid_url);
//            return;
//        }
//        if (UmengUtils.isWechatINstall(activity)) {
//            if (type.equals(COMMONSHARE)) {
//                new ShareAction(activity).withMedia(baseShareSetting(activity, title, picurl, desc, url)).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(shareListener).share();
//            }
//            if (type.equals(VIDEOSHARE)) {
//                new ShareAction(activity).withMedia(baseVideoSetting(activity, title, picurl, desc, url)).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(shareListener).share();
//            }
//        } else {
//            ToastUtils.showToast("请先安装微信客户端");
//        }
//    }
//
//    // 分享朋友圈
//    public static void shareWX_CIRCLE(Activity activity, String title, String picurl, String desc, String url, String type) {
//        if (StringUtils.isEmpty(url)) {
//            ToastUtils.showToast(R.string.sdk_invalid_url);
//            return;
//        }
//        if (UmengUtils.isWechatINstall(activity)) {
//            if (type.equals(COMMONSHARE)) {
//                new ShareAction(activity).withMedia(baseShareSetting(activity, title, picurl, desc, url)).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(shareListener).share();
//            }
//
//            if (type.equals(VIDEOSHARE)) {
//                new ShareAction(activity).withMedia(baseVideoSetting(activity, title, picurl, desc, url)).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(shareListener).share();
//            }
//
//        } else {
//            ToastUtils.showToast("请先安装微信客户端");
//        }
//    }
//
//    private static UMShareListener shareListener = new UMShareListener() {
//        /**
//         * @descrption 分享开始的回调
//         * @param platform 平台类型
//         */
//        @Override
//        public void onStart(SHARE_MEDIA platform) {
//        }
//
//        /**
//         * @descrption 分享成功的回调
//         * @param platform 平台类型
//         */
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            ToastUtils.showToast("分享成功");
//        }
//
//        /**
//         * @descrption 分享失败的回调
//         * @param platform 平台类型
//         * @param t 错误原因
//         */
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            ToastUtils.showToast("分享失败");
//        }
//
//        /**
//         * @descrption 分享取消的回调
//         * @param platform 平台类型
//         */
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            ToastUtils.showToast("取消分享");
//        }
//    };
//
//}
