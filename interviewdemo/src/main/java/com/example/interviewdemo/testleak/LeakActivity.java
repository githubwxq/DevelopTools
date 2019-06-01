package com.example.interviewdemo.testleak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LeakActivity extends AppCompatActivity {

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

        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeakActivity.this.startActivity(new Intent(LeakActivity.this,SingleLeakActivity.class));
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
//        imageViews.clear();
        super.onDestroy();
    }
}
