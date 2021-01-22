package com.wxq.commonlibrary.pay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AliPayUtils {


    /**
     * 通過协议前往转账页面  收款码 解析出url 然后encode 下
     * @param context
     */
    public void goToZhuanzhang(Context context){
        String oo="https://qr.alipay.com/fkx121582ffsej4cg1i607e";
        String url= null;
        try {
            url = URLEncoder.encode(oo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
