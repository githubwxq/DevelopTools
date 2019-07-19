//package com.wxq.commonlibrary.pay;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.support.annotation.IntDef;
//import android.support.v4.util.ArrayMap;
//import android.text.TextUtils;
//
//import com.lzy.okgo.OkGo;
//import com.lzy.okrx2.adapter.FlowableBody;
//import com.wxq.commonlibrary.R;
//import com.wxq.commonlibrary.constant.GlobalContent;
//import com.wxq.commonlibrary.http.converter.NetworkSubscriber;
//import com.wxq.commonlibrary.model.ResponseData;
//import com.wxq.commonlibrary.okgo.converter.JsonConverter;
//import com.wxq.commonlibrary.weiget.DialogManager;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * @author dai
// * @version V_1.0.0
// * @modify Neil
// * @date 2017/10/30
// * @description 3钟方式整合的支付
// */
//public class Pay {
//
//    public static final int ACCOUNT = 0;
//    public static final int ALIPAY = 1;
//    public static final int WEIXIN = 2;
//
//    //订单号码
//    public   String orderNum="";
//
//    private static class PayHolder {
//        private static final Pay pay = new Pay();
//    }
//
//    private Pay() {
//    }
//
//    public static final Pay getInstance() {
//        return PayHolder.pay;
//    }
//
//    private Handler handler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1: {
//                    ArrayMap<String, Object> arrayMap = (ArrayMap<String, Object>) msg.obj;
//                    PayResult payResult = new PayResult((String) arrayMap.get("payResult"));
//                    OnPayListener onPayListener = (OnPayListener) arrayMap.get("listener");
//
//                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//                    String resultStatus = payResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (onPayListener == null) {
//                        return;
//                    }
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        //zhifu
//                        onPayListener.paySuccess(orderNum);
//                    } else {
//                        // 判断resultStatus 为非“9000”则代表可能支付失败
//                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            onPayListener.payFailure(-1, "支付结果确认中");
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            onPayListener.payFailure(-1, "支付失败");
//                        }
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//    };
//
//    @Retention(RetentionPolicy.SOURCE)
//    @Target(ElementType.PARAMETER)
//    @IntDef({ACCOUNT, ALIPAY, WEIXIN})
//    public @interface PayType {
//    }
//
//    public void pay(Builder builder) {
//        final Activity context = builder.context;
//        if (context == null || builder.payType == -1 || TextUtils.isEmpty(builder.url) || TextUtils.isEmpty(builder.json)) {
//            throw new NullPointerException("miss params");
//        }
//        DialogManager.getInstance().createLoadingDialog(context, context.getString(R.string.sdk_onloading)).show();
//        final int payType = builder.payType;
//        final OnPayListener onAccountPayListener = builder.onAccountPayListener;
//        if (onAccountPayListener != null) {
//            onAccountPayListener.beforePay();
//        }
//        OkGo.<ResponseData<String>>post(builder.url)
//                .headers("Uid", builder.uid)
//                .headers(GlobalContent.ACCESSTOKEN, builder.accesstoken)
//                .upJson(builder.json)
//                .converter(new JsonConverter<ResponseData<String>>() {
//                })
//                .adapt(new FlowableBody<ResponseData<String>>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new NetworkSubscriber<String>() {
//                    @Override
//                    protected void onSuccess(String result) {
//                        DialogManager.getInstance().cancelDialog();
//                        try {
//                            JSONObject json = new JSONObject(result);
//                            String key = json.optString("apiKey");
//                            String payInfo = json.optString("payInfo");
//                            String message = json.optString("message");
//                            orderNum = json.optString("orderNum");
//
//
//                            if (payType == ALIPAY) {
//                                ZFBPay.getInstance().pay(context, handler, payInfo, onAccountPayListener);
//                            }
//                            if (payType == WEIXIN) {
//                                BroadcastReceiver receiver = new WXPayResultReceiver(onAccountPayListener);
//                                IntentFilter filter = new IntentFilter("com.Pay");
//                                filter.addAction("com.Pay.error");
//                                filter.addAction("com.Pay.cancle");
//                                context.getApplicationContext().registerReceiver(receiver, filter);
//                                WeiXinPay.getInstance().pay(context, payInfo, key);
//                            }
//                            if (payType == ACCOUNT) {
//                                if (onAccountPayListener != null) {
//                                    if (!TextUtils.isEmpty(key) || !TextUtils.isEmpty(message)) {
//                                        onAccountPayListener.paySuccess(orderNum);
//                                    } else {
//                                        onAccountPayListener.payFailure(-1, "");
//                                    }
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//    public static class Builder {
//        /**
//         * url有两种
//         */
//        public String url = "";
//        public String json = "";
//        public String uid = "";
//        public String accesstoken = "";
//        public Activity context = null;
//        public int payType = -1;
//        public OnPayListener onAccountPayListener;
//
//
//        public Builder uid(String uid) {
//            this.uid = uid;
//            return this;
//        }
//
//        public Builder accesstoken(String accesstoken) {
//            this.accesstoken = accesstoken;
//            return this;
//        }
//
//        public Builder url(String url) {
//            this.url = url;
//            return this;
//        }
//
//        public Builder json(String json) {
//            this.json = json;
//            return this;
//        }
//
//        public Builder context(Activity context) {
//            this.context = context;
//            return this;
//        }
//
//        public Builder payType(@PayType int payType) {
//            this.payType = payType;
//            return this;
//        }
//
//        public Builder OnPayListener(OnPayListener listener) {
//            onAccountPayListener = listener;
//            return this;
//        }
//    }
//
//    class WXPayResultReceiver extends BroadcastReceiver {
//
//        private OnPayListener onPayListener;
//
//        public WXPayResultReceiver(OnPayListener onPayListener) {
//            this.onPayListener = onPayListener;
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            context.unregisterReceiver(this);
//            if (intent.getAction().equals("com.Pay")) {
//                if (onPayListener != null) {
//                    onPayListener.paySuccess(orderNum);
//                }
//            } else if (intent.getAction().equals("com.Pay.error")) {
//
//            } else if (intent.getAction().equals("com.Pay.cancle")) {
//
//            }
//        }
//    }
//
//
//}
