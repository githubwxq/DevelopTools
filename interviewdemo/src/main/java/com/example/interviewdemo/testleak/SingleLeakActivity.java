package com.example.interviewdemo.testleak;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.interviewdemo.R;

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
