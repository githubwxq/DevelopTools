package com.example.interviewdemo.testleak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class SingleLeakActivity extends AppCompatActivity {

    List<ImageView> imageViews=new ArrayList<>();


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_leak);
        for (int i = 0; i < 1000; i++) {
            ImageView imageView=new ImageView(this);
            imageViews.add(imageView);
        }
        IMManager.getInstance(this);

    }
}
