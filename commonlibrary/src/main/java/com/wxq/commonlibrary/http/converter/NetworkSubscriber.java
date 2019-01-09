//package com.wxq.commonlibrary.http.converter;
//
//
//import com.juzi.common.R;
//import com.juzi.common.config.Global;
//import com.juzi.common.http.Response.ResponseData;
//import com.juzi.common.utils.CommonTools;
//import com.juzi.common.utils.DialogManager;
//import com.juzi.common.utils.NetworkUtils;
//import com.juzi.common.utils.StringUtils;
//import com.juzi.common.utils.ToastUtils;
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
//public abstract class NetworkSubscriber<T> implements Subscriber<ResponseData<T>> {
//    public static final String NO_NETWORK = "-1000";
//    public static final String STATUS_SUCCESS = "200";
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
//            CommonTools.outputError(e);
//            if (!NetworkUtils.isNetworkAvailable(Global.application)) {
//                if (!dealHttpException(NO_NETWORK, "", e)) {
//                    ToastUtils.showToast(R.string.common_useless_network);
//                }
//            } else {
//                if (!dealHttpException("", "", e)) {
//                    if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
//                        ToastUtils.showToast(R.string.common_network_weak);
//                    } else if (e != null && !android.text.TextUtils.isEmpty(e.getMessage()) && e.getMessage().length() <= ERROR_MSG_MAX_LENGTH){
//                        ToastUtils.showToast(e.getMessage());
//                    } else {
//                        ToastUtils.showToast(R.string.common_fail_to_request);
//                    }
//                }
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onNext(ResponseData<T> tResponseData) {
//        try {
//            currentTimeStemp = tResponseData.timeStamp;
//            if (STATUS_SUCCESS.equals(tResponseData.status)) {
//               //TODO:rxcache bug 解决方式
//                onSuccess(tResponseData.content);
//            } else {
//                DialogManager.getInstance().cancelDialog();
//                if (STATUS_SERVER_MAINTAIN.equals(tResponseData.status)) {
//                    return;
//                }
//                if (!dealHttpException(tResponseData.status, tResponseData.errorMsg, null)) {
//                    if (StringUtils.isEmpty(tResponseData.errorMsg) || tResponseData.errorMsg.length() > ERROR_MSG_MAX_LENGTH) {
//                        ToastUtils.showToast(R.string.common_fail_to_request);
//                    } else {
//                        if (tResponseData.errorMsg.length() > ERROR_MSG_MAX_LENGTH) {
//                            ToastUtils.showToast(R.string.common_fail_to_request);
//                        } else {
//                            ToastUtils.showToast(tResponseData.errorMsg);
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
//    protected boolean dealHttpException(String code, String errorMsg, Throwable e) {
//        return false;
//    }
//}
