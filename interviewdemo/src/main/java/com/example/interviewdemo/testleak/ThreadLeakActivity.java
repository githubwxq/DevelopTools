package com.example.interviewdemo.testleak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.interviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ThreadLeakActivity extends AppCompatActivity {

    List<ImageView> imageViews=new ArrayList<>();

    TextView tv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        for (int i = 0; i < 1000; i++) {
            ImageView imageView=new ImageView(this);
            imageViews.add(imageView);
        }

//        、、开启县城
        LeakThread leakThread = new LeakThread();
        leakThread.start();
        tv_test=findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThreadLeakActivity.this.startActivity(new Intent(ThreadLeakActivity.this,SingleLeakActivity.class));
            }
        });

    }

    class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {

//      imageViews.clear();
        super.onDestroy();

    }
}


///**
//     * 正确处理方式
//     */
//private static class MyThread extends Thread {
//    SoftReference<Activity> context;
//
//    MyThread(Activity activity) {
//        context = new SoftReference<>(activity);
//    }
//
//    @Override
//    public void run() {
//        SystemClock.sleep(1000 * 15);
//        String endTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss SSS", Locale.getDefault()).format(new Date());
//        Log.i("bqt", "【结束休息】" + endTime);//即使Activity【onDestroy被回调了】，这条日志仍会打出来
//        if (context.get() != null) {
//            context.get().runOnUiThread(() -> Toast.makeText(context.get(), "结束休息", Toast.LENGTH_SHORT).show());
//        }
//    }
//}