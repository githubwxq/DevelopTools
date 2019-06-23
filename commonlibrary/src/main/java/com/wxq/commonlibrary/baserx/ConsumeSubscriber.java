//package com.wxq.commonlibrary.baserx;
//import android.util.Log;
//
//import com.wxq.commonlibrary.R;
//import com.wxq.commonlibrary.model.KlookResponseData;
//import com.wxq.commonlibrary.util.NetworkUtils;
//import com.wxq.commonlibrary.util.StringUtils;
//import com.wxq.commonlibrary.util.ToastUtils;
//import com.wxq.commonlibrary.weiget.DialogManager;
//
//import org.reactivestreams.Subscriber;
//import org.reactivestreams.Subscription;
//
//import java.net.ConnectException;
//import java.net.SocketTimeoutException;
//
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2017/4/30
// * @description 网络请求基本订阅者
// */
//public abstract class ConsumeSubscriber<T> implements Subscriber<Optional<T>> {
//    public static final int NO_NETWORK = -1000;
//    public static final int STATUS_SUCCESS = 200;
//    public static final int ERROR_MSG_MAX_LENGTH = 20;
//    /**
//     * 服务器维护
//     */
//    public static final String STATUS_SERVER_MAINTAIN = "5050";
//
//    public String currentTimeStemp;
//
//
//    @Override
//    public void onSubscribe(Subscription s) {
//        s.request(Long.MAX_VALUE);
//    }
//
//    @Override
//    public void onComplete() {
//        DialogManager.getInstance().cancelDialog();
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        try {
//            DialogManager.getInstance().cancelDialog();
//            Log.e("error",e.getMessage());
//            if (!NetworkUtils.isAvailableByPing()) {
//                if (!dealHttpException(NO_NETWORK, "", e)) {
//                    ToastUtils.showShort(R.string.common_useless_network);
//                }
//            } else {
//                if (!dealHttpException(0, "", e)) {
//                    if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
//                        ToastUtils.showShort(R.string.common_network_weak);
//                    } else if (e != null && !android.text.TextUtils.isEmpty(e.getMessage()) && e.getMessage().length() <= ERROR_MSG_MAX_LENGTH){
//                        ToastUtils.showShort(e.getMessage());
//                    } else {
//                        ToastUtils.showShort(R.string.common_fail_to_request);
//                    }
//                }
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onNext(Optional<T> tResponseData) {
//        try {
//            currentTimeStemp = tResponseData.timeStamp;
//            if (STATUS_SUCCESS==(tResponseData.code)) {
//               //TODO:rxcache bug 解决方式
//                onSuccess(tResponseData.data);
//            } else {
//                DialogManager.getInstance().cancelDialog();
//                if (STATUS_SERVER_MAINTAIN.equals(tResponseData.code)) {
//                    return;
//                }
//                if (!dealHttpException(tResponseData.code, tResponseData.msg, null)) {
//                    if (StringUtils.isEmpty(tResponseData.msg) || tResponseData.msg.length() > ERROR_MSG_MAX_LENGTH) {
//                        ToastUtils.showShort(R.string.common_fail_to_request);
//                    } else {
//                        if (tResponseData.msg.length() > ERROR_MSG_MAX_LENGTH) {
//                            ToastUtils.showShort(R.string.common_fail_to_request);
//                        } else {
//                            ToastUtils.showShort(tResponseData.msg);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            onError(e);
//        }
//    }
//
//    protected abstract void onSuccess(T t);
//
//
//    protected boolean dealHttpException(int code, String errorMsg, Throwable e) {
//        return false;
//    }
//}
