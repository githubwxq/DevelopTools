package com.example.interviewdemo.testleak;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;

import com.example.interviewdemo.R;
import com.wxq.commonlibrary.util.ToastUtils;

public class HandlerLeakActivity extends AppCompatActivity {
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_leak);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort("11211212");
            }
        },10000);

    }


    /**
     * 正确处理方式
     */
 /*   private static class MyHandler extends Handler {
        private final WeakReference<HandlerLeakActivity> mActivity;

        public MyHandler(HandlerLeakActivity activity) {
            mActivity = new WeakReference<HandlerLeakActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerLeakActivity activity = mActivity.get();
            if (activity != null) {
                //do Something
            }
        }
    }
*/


}



