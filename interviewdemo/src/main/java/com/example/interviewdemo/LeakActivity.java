package com.example.interviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LeakActivity extends AppCompatActivity {

    List<ImageView> imageViews=new ArrayList<>();

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
