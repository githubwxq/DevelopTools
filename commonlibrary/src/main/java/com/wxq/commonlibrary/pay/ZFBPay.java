package com.wxq.commonlibrary.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;

import com.alipay.sdk.app.PayTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by zh on 2017/10/10.
 * describe: 支付宝支付
 */

public class ZFBPay {
    // 商户PID
    public static final String PARTNER = "2088411072299364";
    // 商户收款账号
    public static final String SELLER = "1790267141@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ09/x+MYqfmR0LqIw0KBQcl8F1vGqtOSEp+mEdKoeTgv2bxtKIzKunLX83D6eBQcbGch6saXUrIZgSyHyZRggGzHIxDLHNaIYszbV1vrhN8hYd5SHabDLOwbHOEQOjhCIaRJBJlPShpn1wlzrpIVjlxvy73rhakzXXvpI0FknppAgMBAAECgYA6jMI5qhl2MW3pgatpiIiUv9C/ycYhcXXDn13udeDQi8tZdrjvCKR7B8p1oPSuHOYo34M4+Aky9mneZ8DnkMQabl0VViDIT2odOvELqeLBdhI66OWViKeqYkor9bMV9uSsFTa/h5UJwqYC+9pukDfZ7gxBL2oL17SgeUtxkjPuAQJBANFZt0KfETUpiiZWivQaj/0auWv6vFjnA68wESaFThcMRuDmSR1O7fng88YbEymUGdmyKRN9lNiaxEKlj1ry2kECQQDAR8q9fuQxeBDGnGmcGlg3Nth3vDWKV0E5uLG3va9vPwgZtEovWGje7LTJxFluWyBBgz+n1cr4ua8gnOU/kAYpAkBjQLSoyj9fQ/1yZa9lQb6oUeY88lgfkg7mHNTUvXijZren4qYhVg1vXZ5VevqfyM5krpnY2r4Z325S5qlLhj3BAkA5W7ExAg9UanqmpLYkaP9zyRqd7TkTgZ/ldiEdrKoOx4DFGjEfGoJ+LaJopff/oZNnt51flbkspUeGtQb2BSKxAkAhuxOkXpJc9dpugpVj0AyiFvA6dAAFK2pAifwn9hqgPt4KRrIIgWmVXaNK0MpD33l7U7cdBqM4h3m4JcS4R1FZ";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private static ZFBPay instance = null;

    public static ZFBPay getInstance() {
        if (instance == null) {
            instance = new ZFBPay();
        }
        return instance;
    }

    public void pay(final Activity activity, final Handler mHandler, final String payInfo, final OnPayListener onPayListener) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = mHandler.obtainMessage(SDK_PAY_FLAG);
                ArrayMap<String, Object> arrayMap = new ArrayMap<>();
                arrayMap.put("payResult", result);
                arrayMap.put("listener", onPayListener);
                msg.obj = arrayMap;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String p_name, String p_desc, String price, String orderId, String notify_url) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + p_name + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + p_desc + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


}
