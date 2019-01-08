package com.example.module_login.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.module_login.activity.LoginActivity;
import com.juziwl.uilibrary.notification.NotificationUtils;

import cn.bmob.push.PushConstants;

//TODO 集成：1.3、创建自定义的推送消息接收器，并在清单文件中注册
public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            String msg=intent.getStringExtra("msg");
            NotificationUtils.showCommonNofition(context,"title",msg,new Intent(context, LoginActivity.class));
        }
    }
}