package com.wxq.commonlibrary.baserx;

import android.util.Log;

import com.wxq.commonlibrary.R;
import com.wxq.commonlibrary.http.ApiException;
import com.wxq.commonlibrary.util.NetworkUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/4/30
 * @description 网络请求基本订阅者
 */
public abstract class RxSubscriber<T> implements Subscriber<T> {
    public static final int NO_NETWORK = -1000;
    public static final int STATUS_SUCCESS = 200;
    public static final int ERROR_MSG_MAX_LENGTH = 20;
    /**
     * 服务器维护
     */
    public static final String STATUS_SERVER_MAINTAIN = "5050";

    public String currentTimeStemp;

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onComplete() {

    }


    @Override
    public void onError(Throwable e) {
        try {
            Log.e("error",e.getMessage());
            if (!NetworkUtils.isAvailableByPing()) {
                if (!dealHttpException(NO_NETWORK, "", e)) {
                    ToastUtils.showShort(R.string.common_useless_network);
                }
            } else {
                if (!dealHttpException(0, "", e)) {
                    if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
                        ToastUtils.showShort(R.string.common_network_weak);
                    } else if (e != null && !android.text.TextUtils.isEmpty(e.getMessage()) && e.getMessage().length() <= ERROR_MSG_MAX_LENGTH){
                        ToastUtils.showShort(e.getMessage());
                    } if (e instanceof ApiException) {
                        String displayMessage = ((ApiException) e).getDisplayMessage();
                        ToastUtils.showShort(displayMessage);
                    }else {
                        ToastUtils.showShort(R.string.common_fail_to_request);
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onNext(T tResponseData) {
        try {
           onSuccess(tResponseData);
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }

    protected abstract void onSuccess(T t);


    protected boolean dealHttpException(int code, String errorMsg, Throwable e) {
        return false;
    }
}
