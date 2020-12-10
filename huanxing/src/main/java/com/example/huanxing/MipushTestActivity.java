package com.example.huanxing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.umeng.message.UmengNotifyClickActivity;
import com.umeng.message.entity.UMessage;
import com.wxq.commonlibrary.http.common.LogUtil;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class MipushTestActivity extends UmengNotifyClickActivity {

    private static String TAG = MipushTestActivity.class.getName();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mipush);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Message message = Message.obtain();
        message.obj = body;
        handler.sendMessage(message);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            String body = (String) msg.obj;
            UMessage var3 = null;

            try {
                var3 = new UMessage(new JSONObject(body));
                if (var3.extra != null) {
//                    UmengInitUtils.dealWithUmengMessage(MiPushActivity.this, var3, true);
                    LogUtil.e("var3.extra"+var3.extra);
                } else {
                    startActivity(new Intent(MipushTestActivity.this, MainActivity.class));
                }
            } catch (JSONException var5) {
                var5.printStackTrace();
                startActivity(new Intent(MipushTestActivity.this, MainActivity.class));
            } finally {
                finish();
            }
            return false;
        }
    });
}