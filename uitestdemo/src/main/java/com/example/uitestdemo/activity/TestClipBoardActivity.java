package com.example.uitestdemo.activity;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.uitestdemo.ClipBoardListenerService;
import com.example.uitestdemo.R;
import com.example.uitestdemo.R2;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.AppUtils;


import java.util.List;

import butterknife.BindView;

public class TestClipBoardActivity extends BaseActivity {

    @BindView(R2.id.tv_set_data)
    TextView tvSetData;
    @BindView(R2.id.tv_get_data)
    TextView tvGetData;

    @Override
    protected void initViews() {

        tvSetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //剪切板设置内容
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText("text", "1234567"));
            }
        });

        //启动服务监测剪切变中的数据

        startService(new Intent(TestClipBoardActivity.this, ClipBoardListenerService.class));


    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_clip_board;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("wxq","onPauseisback"+Foreground.get().isForeground());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("wxq","onPause"+ AppUtils.isAppForeground());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("wxq","onResume"+AppUtils.isAppForeground());
    }


    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("wxq", appProcess.processName);
                    return true;
                } else {
                    Log.i("wxq", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


    private boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
            if (list.get(0).baseActivity.getPackageName().equals("com.example.uitestdemo")) {
                return true;
            }
        return false;
    }
}
