package com.example.uitestdemo;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.wxq.commonlibrary.util.ClipboardUtils;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/02
 * desc:
 * version:1.0
 */
public class ClipBoardListenerService extends Service {

    // 是否有前往e课的指令
    public static boolean hasKouLing=false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("wxq", "service" + "create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wxq", "service" + "onStartCommand");
        ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cb.setPrimaryClip(ClipData.newPlainText("", ""));
        cb.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                // 具体实现
                CharSequence text = ClipboardUtils.getText(ClipBoardListenerService.this);
                Log.e("wxq", "clipboard==========" + text);
                String  kouLing=text.toString();
                if (kouLing.contains("COURSE_ID")&& kouLing.contains("REGION_ID")) {
                    hasKouLing=true;





                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("wxq", "service" + "onDestroy");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("wxq", "service" + "onBind");

        return null;
    }


}
